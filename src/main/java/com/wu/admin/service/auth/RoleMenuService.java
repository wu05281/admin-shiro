package com.wu.admin.service.auth;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.pojo.auth.entity.MenuDO;
import com.wu.admin.pojo.auth.entity.RoleMenuDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author : wubo
 *         Date : 2017/10/12
 *         Time : 上午9:49
 */
@Service
public class RoleMenuService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DaoSupport daoSupport;


    @Transactional(readOnly = true)
    public Set<String> getMenuCodesByRoleId(String roleId) {
        List<MenuDO> menus = getMenusByRoleId(roleId, false);
        Set<String> menusCode = new HashSet<String>();
        menus.forEach(menu -> menusCode.add(menu.getMenuCode()));
        return menusCode;
    }

    /**
     * @param roleId
     * @param onlyMenu 区分是用来生成所有权限code，还是仅展示左侧菜单树
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MenuDO> getMenusByRoleId(String roleId, Boolean onlyMenu) {
        Criteria rmCtr = daoSupport.createCriteria(RoleMenuDO.class);
        rmCtr.add(Restrictions.eq("roleId", roleId));
        List<RoleMenuDO> roleMenus = rmCtr.list();

        Criteria menuCtr = daoSupport.createCriteria(MenuDO.class);
        if (onlyMenu) {
            menuCtr.add(Restrictions.eq("menuType", "menu"));
        }
        List<MenuDO> menus = menuCtr.list();

        List<MenuDO> result = new ArrayList<MenuDO>();
        menus.forEach(menu -> {
                    roleMenus.forEach(roleMenu -> {
                        if (menu.getMenuId().equals(roleMenu.getMenuId())) {
                            result.add(menu);
                        }
                    });
                }
        );
        return result;
    }

    @Transactional(readOnly = true)
    public List<RoleMenuDO> listByRoleId(String roleId) {
        Criteria criteria = daoSupport.createCriteria(RoleMenuDO.class);
        criteria.add(Restrictions.eq("roleId", roleId));
        return criteria.list();
    }

    @Transactional(rollbackFor = {Exception.class,})
    public void updateMenus(String roleId, String[] menus) {
        Criteria criteria = daoSupport.createCriteria(RoleMenuDO.class);
        criteria.add(Restrictions.eq("roleId", roleId));
        List<RoleMenuDO> original = criteria.list();
        //找出需要删除的权限，以及需要insert的权限；
        List<String> needInsertMenus = new ArrayList<String>();
        if (original == null || original.size() == 0) {
            needInsertMenus = Arrays.asList(menus);
        } else {
            for (String menu : menus) {
                Iterator<RoleMenuDO> it = original.iterator();
                boolean notExist = true;
                while (it.hasNext()) {
                    RoleMenuDO roleMenuDO = it.next();
                    if (Objects.equals(menu, roleMenuDO.getMenuId())) {
                        it.remove();
                        notExist = false;
                    }
                }
                if (notExist) {
                    needInsertMenus.add(menu);
                }
            }
        }

        //处理完剩下的original即为需要删除的
        for (RoleMenuDO roleMenuDO : original) {
            logger.info("删除掉id：{}", roleMenuDO.getId());
            daoSupport.deleteEntity(roleMenuDO);
        }

        for (String menuId : needInsertMenus) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setMenuId(menuId);
            roleMenuDO.setRoleId(roleId);
            daoSupport.save(roleMenuDO);
        }
//        throw new NullPointerException("测试一个异常回滚情况是否发生！");
    }
}
