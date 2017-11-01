package com.wu.admin.web.controller.auth;


import com.wu.admin.utils.MenuTreeUtil;
import com.wu.admin.base.Pagination;
import com.wu.admin.pojo.auth.dto.ResponseDTO;
import com.wu.admin.pojo.auth.entity.MenuDO;
import com.wu.admin.pojo.auth.entity.RoleDO;
import com.wu.admin.pojo.auth.entity.RoleMenuDO;
import com.wu.admin.service.auth.MenuService;
import com.wu.admin.service.auth.RoleMenuService;
import com.wu.admin.service.auth.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author wubo
 * @version 创建时间 : 2017 10 18 下午4:51
 */
@Controller
@RequestMapping(value = "/role")
public class RoleCtl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RoleService roleService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private MenuService menuService;

    @RequiresPermissions("role:index")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "auth/role_index";
    }

    //    @RequiresPermissions("role:index")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseDTO<Pagination> list() {
        logger.info("开始查询role list");
        Pagination<RoleDO> pagination = roleService.pageRoleByConditions(1L, 20L, null);
        return new ResponseDTO<>(0, "", pagination);
    }

    @RequestMapping(value = "/menu_tree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO comboTree(@RequestParam String roleId) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        List<MenuDO> menuList = menuService.listAllMenus();
        List<RoleMenuDO> roleMenuList = roleMenuService.listByRoleId(roleId);
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuList, roleMenuList);
        List<Map<String, Object>> mapList = menuTreeUtil.buildTree();
        ResponseDTO responseDTO = new ResponseDTO(1, "", mapList);
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public ResponseDTO grant(String roleId, String[] menuIds) {
        logger.info("更新权限的角色id为：{}, 新权限ids为：{}", roleId, menuIds);
        try {
            roleMenuService.updateMenus(roleId, menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO responseDTO = new ResponseDTO(0, "保存失败，本页面将自动关闭！", null);
        }
        ResponseDTO responseDTO = new ResponseDTO(1, "保存成功，本页面将自动关闭！", null);
        return responseDTO;
    }

    @RequiresPermissions("role:save")
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() {

        return "auth/role_add";
    }


    @RequiresPermissions("role:save")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<?> add(@RequestParam String roleName, @RequestParam String roleDesc, @RequestParam String roleEnable) {
        logger.info("请求参数：{},{},{}", roleName, roleDesc, roleEnable);
        roleService.addRole(roleName, roleDesc, Objects.equals("0", roleEnable) ? Boolean.FALSE : Boolean.TRUE);
        ResponseDTO responseDTO = new ResponseDTO(1, "sucess", null);
        logger.info("返回的对象为：{}", responseDTO);
        return responseDTO;
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/del/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseDTO<?> del(@PathVariable String roleId) {
        logger.info("请求参数：{}", roleId);
        roleService.delRole(roleId);
        ResponseDTO responseDTO = new ResponseDTO(1, "sucess", null);
        return responseDTO;
    }

}
