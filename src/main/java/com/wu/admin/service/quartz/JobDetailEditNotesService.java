package com.wu.admin.service.quartz;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.pojo.quartz.ao.JobDetailEditAO;
import com.wu.admin.pojo.quartz.entity.JobDetailEditNotesDO;
import com.wu.admin.utils.SessionHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 30 下午5:38
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Service
public class JobDetailEditNotesService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DaoSupport daoSupport;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void addEditNote(JobDetailEditAO ao){
        JobDetailEditNotesDO jobDetailEditNotesDO = new JobDetailEditNotesDO();
        BeanUtils.copyProperties(ao, jobDetailEditNotesDO);
        jobDetailEditNotesDO.setCron(ao.getJobDetailDO().getCron());
        jobDetailEditNotesDO.setState(ao.getJobDetailDO().getState());
        jobDetailEditNotesDO.setOperatorId(SessionHolderUtils.getCurrentUid());
        jobDetailEditNotesDO.setOperatorName(SessionHolderUtils.getCurrentUname());
        daoSupport.save(jobDetailEditNotesDO);
    }
}
