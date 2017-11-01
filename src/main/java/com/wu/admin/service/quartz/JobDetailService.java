package com.wu.admin.service.quartz;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.base.Pagination;
import com.wu.admin.enums.JobDetailEditTypeEnum;
import com.wu.admin.exception.BusinessException;
import com.wu.admin.exception.ExceptionEnum;
import com.wu.admin.pojo.quartz.ao.JobDetailEditAO;
import com.wu.admin.pojo.quartz.entity.JobDetailDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 * and ( 1 or 2 ) :criteria.add(Restrictions.or(Restrictions.like("groupCode", values), Restrictions.like("nameCode", values)));
 * <p>
 * <p>
 * Time : 2017 10 25 上午11:46
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Service
public class JobDetailService {

    @Resource
    private DaoSupport daoSupport;

    @Resource
    private InitJobService initJobService;

    @Resource
    private JobDetailEditNotesService jobDetailEditNotesService;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<JobDetailDO> listActiveJobs() {
        Criteria criteria = daoSupport.createCriteria(JobDetailDO.class);
        criteria.add(Restrictions.eq("state", 1));
        return criteria.list();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String getCallUrlById(Integer id) {
        Criteria criteria = daoSupport.createCriteria(JobDetailDO.class);
        criteria.add(Restrictions.eq("id", id));
        return (String) criteria.setProjection(Projections.property("callUrl")).uniqueResult();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination<JobDetailDO> pageByConditions(Integer pageIndex, Integer pageSize, String desc, Integer state) {
        Criteria criteria = daoSupport.createCriteria(JobDetailDO.class);
        if (!StringUtils.isEmpty(desc)) {
            criteria.add(Restrictions.like("description", "%" + desc + "%"));
        }
        if (state != 0) {
            criteria.add(Restrictions.eq("state", state));
        }
        //先获取总记录数
        Long totalCount = (Long) criteria.setProjection(Projections.count("id")).uniqueResult();

        criteria.setProjection(null);
        criteria.setFirstResult((pageIndex - 1) * pageSize);
        criteria.setMaxResults(pageIndex * pageSize);
        List items = criteria.list();

        Pagination<JobDetailDO> pagination = new Pagination<JobDetailDO>(pageIndex.longValue(), pageSize.longValue(), totalCount);
        pagination.setItems(items);
        return pagination;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addJobDetail(JobDetailDO jobDetailDO) {
        try {
            Integer id = (Integer) daoSupport.save(jobDetailDO);
            jobDetailDO.setId(id);
            initJobService.addJob(jobDetailDO);
        } catch (ConstraintViolationException e) {
            throw new BusinessException(ExceptionEnum.DUPLICAT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnum.FAIL);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateJobDetail(Integer id, String newCron) {
        JobDetailDO jobDetailDO = daoSupport.get(id, JobDetailDO.class);
        if (jobDetailDO == null) {
            throw new BusinessException(ExceptionEnum.FAIL);
        }

        String blank = " ";
        if (jobDetailDO.getCron().replaceAll(blank, "").equals(newCron.replaceAll(blank, ""))) {
            return;
        }
        //先保留原状态，原表达式。
        Integer origState = jobDetailDO.getState();
        String origCron = jobDetailDO.getCron();

        jobDetailDO.setCron(newCron);
        try {
            initJobService.addJob(jobDetailDO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //增加操作记录
        JobDetailEditAO jobDetailEditAO = buildJobDetailEditAO(origState, origCron, JobDetailEditTypeEnum.EDIT_CRON.getCode(), jobDetailDO);
        jobDetailEditNotesService.addEditNote(jobDetailEditAO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void runNow(Integer id) {
        JobDetailDO jobDetailDO = daoSupport.get(id, JobDetailDO.class);
        if (Objects.isNull(jobDetailDO) || jobDetailDO.getState().intValue() != 1) {
            throw new BusinessException(ExceptionEnum.FAIL);
        }
        //调用任务处理service，立即触发一次接口调用。
        try {
            initJobService.runJobNow(jobDetailDO);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnum.FAIL);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void stopNow(Integer id) {
        JobDetailDO jobDetailDO = daoSupport.get(id, JobDetailDO.class);
        if (Objects.isNull(jobDetailDO) || jobDetailDO.getState().intValue() != 1) {
            throw new BusinessException(ExceptionEnum.FAIL);
        }
        //先保留原状态，原表达式。
        Integer origState = jobDetailDO.getState();
        String origCron = jobDetailDO.getCron();
        //调用任务处理service，删除job。
        jobDetailDO.setState(2);
        try {
            initJobService.deleteJob(jobDetailDO);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnum.FAIL);
        }
        //增加操作记录
        JobDetailEditAO jobDetailEditAO = buildJobDetailEditAO(origState, origCron, JobDetailEditTypeEnum.DELETE.getCode(), jobDetailDO);
        jobDetailEditNotesService.addEditNote(jobDetailEditAO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resumeNow(Integer id) {
        JobDetailDO jobDetailDO = daoSupport.get(id, JobDetailDO.class);
        if (Objects.isNull(jobDetailDO) || jobDetailDO.getState().intValue() != 2) {
            throw new BusinessException(ExceptionEnum.FAIL);
        }
        //先保留原状态，原表达式。
        Integer origState = jobDetailDO.getState();
        String origCron = jobDetailDO.getCron();
        //调用任务处理service，添加job。
        jobDetailDO.setState(1);
        try {
            initJobService.addJob(jobDetailDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnum.FAIL);
        }
        //增加操作记录
        JobDetailEditAO jobDetailEditAO = buildJobDetailEditAO(origState, origCron, JobDetailEditTypeEnum.RESUME.getCode(), jobDetailDO);
        jobDetailEditNotesService.addEditNote(jobDetailEditAO);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delJob(Integer id) {
        JobDetailDO jobDetailDO = daoSupport.get(id, JobDetailDO.class);
        if (Objects.isNull(jobDetailDO)) {
            return;
        }
        daoSupport.deleteEntity(jobDetailDO);
        try {
            initJobService.deleteJob(jobDetailDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionEnum.FAIL);
        }
    }

    private JobDetailEditAO buildJobDetailEditAO(Integer origState, String origCron, Integer editType, JobDetailDO jobDetailDO) {
        //增加操作记录
        JobDetailEditAO jobDetailEditAO = new JobDetailEditAO();
        jobDetailEditAO.setJobDetailDO(jobDetailDO);
        jobDetailEditAO.setOrigCron(origCron);
        jobDetailEditAO.setOrigState(origState);
        jobDetailEditAO.setEditType(editType);
        return jobDetailEditAO;
    }
}
