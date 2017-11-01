package com.wu.admin.service.auth;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.base.Pagination;
import com.wu.admin.exception.BusinessException;
import com.wu.admin.exception.ExceptionEnum;
import com.wu.admin.pojo.auth.entity.AdminDO;
import com.wu.admin.pojo.auth.entity.RoleDO;
import com.wu.admin.utils.PasswordUtil;
import com.wu.admin.utils.SessionHolderUtils;
import com.wu.admin.utils.SnowflakeIdWorker;
import com.wu.admin.web.ao.AdminAddAO;
import com.wu.admin.web.ao.ChangePasswdAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 * @author : wubo
 * Date : 2017/10/10
 * Time : 下午6:02
 */
@Service
public class AdminService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DaoSupport daoSupport;

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public AdminDO findByUserName(String userName){
        Criteria criteria = daoSupport.createCriteria(AdminDO.class);
        criteria.add(Restrictions.eq("userName", userName));
        Object o = criteria.list().get(0);
        if (Objects.nonNull(o)){
            return (AdminDO)o;
        }else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Pagination<AdminDO> pageAdminsByConditions(Integer pageIndex, Integer pageSize, Map<String, Object> params){
        //获取记录总数
        Criteria criteria = daoSupport.createCriteria(AdminDO.class);
        //相当于 select count(uid)
        criteria.setProjection(Projections.count("uid"));
        //设置查询条件
        if (Objects.nonNull(params)) {
            criteria.add(Restrictions.allEq(params));
        }
        criteria.add(Restrictions.eq("editEnable", Boolean.TRUE));
        Long total = (Long) criteria.uniqueResult();

        //开始查询列表,首先清除count查询所用Projection
        criteria.setProjection(null);
        criteria.setFirstResult((pageIndex - 1)* pageSize);
        criteria.setMaxResults(pageIndex * pageSize);
        List<AdminDO> admins = criteria.list();

        Pagination<AdminDO> pagination = new Pagination<AdminDO>(Long.valueOf(pageIndex), Long.valueOf(pageSize), total);
        pagination.setItems(admins);
        return pagination;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addAdmin(AdminAddAO ao) {
        AdminDO adminDO = new AdminDO();
        BeanUtils.copyProperties(ao, adminDO);
        RoleDO roleDO = daoSupport.get(ao.getRoleId(), RoleDO.class);
        adminDO.setPassword(PasswordUtil.createAdminPwd(adminDO.getPassword(), adminDO.getCredentialsSalt()));
        adminDO.setUid(String.valueOf(snowflakeIdWorker.nextId()));
        adminDO.setRole(roleDO);
        daoSupport.save(adminDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editPassword(ChangePasswdAO ao) {
        AdminDO adminDO = daoSupport.get(SessionHolderUtils.getCurrentUid(),AdminDO.class);
        String origPassword = PasswordUtil.createAdminPwd(ao.getOrigPassword(), adminDO.getCredentialsSalt());
        if (!Objects.equals(origPassword, adminDO.getPassword())) {
            throw new BusinessException(ExceptionEnum.PASSWORD_ORIGIN_FAILED);
        }
        adminDO.setPassword(PasswordUtil.createAdminPwd(ao.getNewPassword(), adminDO.getCredentialsSalt()));
    }
}
