package com.wu.admin.service.auth;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.pojo.auth.entity.MenuDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午6:03
 */
@Service
public class MenuService {

    @Autowired
    private DaoSupport daoSupport;

    @Transactional(readOnly = true)
    public Set<String> getAllMenuCode(){
        List<String> result = (List<String>) daoSupport.listByHql(String.class,"select menuId from Menu ", new Object());
        Set<String> menuCodes = new HashSet<>();
        menuCodes.addAll(result);
        return menuCodes;
    }

    public List<MenuDO> listAllMenus(){
        Criteria criteria = daoSupport.createCriteria(MenuDO.class);
        criteria.add(Restrictions.eq("editEnable", Boolean.TRUE));
        return  criteria.list();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void del(String menuId){
        daoSupport.delete(menuId, MenuDO.class);
        daoSupport.flush();
        throw new NullPointerException("测试一个异常回滚情况是否发生！");
    }
}
