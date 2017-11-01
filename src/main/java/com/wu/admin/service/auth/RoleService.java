package com.wu.admin.service.auth;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.base.Pagination;
import com.wu.admin.exception.BusinessException;
import com.wu.admin.exception.ExceptionEnum;
import com.wu.admin.pojo.auth.entity.RoleDO;
import com.wu.admin.utils.SnowflakeIdWorker;
import com.wu.admin.web.vo.auth.RoleOptionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午6:02
 */
@Service
public class RoleService {

    @Resource
    private DaoSupport daoSupport;


    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    public Pagination<RoleDO> pageRoleByConditions(Long pageIndex, Long pageSize, Map params) {
        Criteria criteria = daoSupport.createCriteria(RoleDO.class);
        criteria.add(Restrictions.eq("editEnable",Boolean.TRUE));
        Long totalCount = (Long) criteria.setProjection(Projections.count("roleId")).uniqueResult();

        criteria.setProjection(null);
        criteria.setFirstResult((pageIndex.intValue() - 1) * pageSize.intValue());
        criteria.setMaxResults(pageIndex.intValue()* pageSize.intValue());
        List<RoleDO> items = criteria.list();
        Pagination<RoleDO> pagination = new Pagination<RoleDO>(pageIndex, pageSize, totalCount);
        pagination.setItems(items);
        return pagination;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void addRole(String roleName, String roleDesc, Boolean enable) {
        //是否存在同名角色。
        Criteria criteria = daoSupport.createCriteria(RoleDO.class);
        criteria.add(Restrictions.eq("roleName" ,roleName));
        Long count = (Long)criteria.setProjection(Projections.count("roleId")).uniqueResult();
        if (count.intValue() != 0) {
            throw new BusinessException(ExceptionEnum.DUPLICAT);
        } else {
            RoleDO roleDO = new RoleDO();
            roleDO.setRoleId(String.valueOf(snowflakeIdWorker.nextId()));
            roleDO.setRoleName(roleName);
            roleDO.setRoleDesc(roleDesc);
            roleDO.setEnable(enable);
            roleDO.setEditEnable(Boolean.TRUE);
            daoSupport.save(roleDO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delRole(String roleId) {
        RoleDO roleDO = daoSupport.get(roleId, RoleDO.class);
        if (roleDO == null || !roleDO.getEditEnable()) {
            throw new BusinessException(ExceptionEnum.NOT_EXSIT);
        } else {
            daoSupport.deleteEntity(roleDO);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<RoleOptionVO> listRoleOptions(){
        String hql = "select r.roleId as roleId, r.roleName as roleName from RoleDO r ";
        return daoSupport.listByHql(RoleOptionVO.class,hql);
    }

}
