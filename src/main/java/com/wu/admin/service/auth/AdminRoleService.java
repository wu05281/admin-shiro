package com.wu.admin.service.auth;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.pojo.auth.entity.AdminRoleDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午6:17
 */
@Service
public class AdminRoleService {

    @Autowired
    private DaoSupport daoSupport;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String getRoleIdByAdminId(String adminId){
        Criteria criteria = daoSupport.createCriteria(AdminRoleDO.class);
        criteria.add(Restrictions.eq("adminId",adminId));
        AdminRoleDO adminRole = (AdminRoleDO) criteria.list().get(0);
        if (Objects.isNull(adminRole)) {
            return null;
        } else {
            return adminRole.getRoleId();
        }
    }
}
