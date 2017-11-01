package com.wu.admin.web.controller;

import com.wu.admin.pojo.auth.entity.AdminDO;
import com.wu.admin.pojo.auth.entity.MenuDO;
import com.wu.admin.pojo.quartz.dto.JobRequestDTO;
import com.wu.admin.pojo.quartz.dto.JobResponseDTO;
import com.wu.admin.service.auth.AdminRoleService;
import com.wu.admin.service.auth.MenuService;
import com.wu.admin.service.auth.RoleMenuService;
import com.wu.admin.utils.MenuTreeUtil;
import com.wu.admin.web.vo.AdminInfoVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wubo on 2017/10/3.
 */
@Controller
public class IndexCtl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private AdminRoleService adminRoleService;

    @Resource
    private MenuService menuService;

    @RequiresAuthentication
    @RequestMapping(path = {"/index","/"} ,method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("name","Alex");
        Subject subject = SecurityUtils.getSubject();
        AdminDO admin = (AdminDO) subject.getPrincipal();
        AdminInfoVO adminInfoVO = new AdminInfoVO();

        BeanUtils.copyProperties(admin, adminInfoVO);
        adminInfoVO.setRoleId(admin.getRole().getRoleId());

        model.addAttribute("admin", adminInfoVO);

        List<MenuDO> menuLists = roleMenuService.getMenusByRoleId(admin.getRole().getRoleId(), true);

        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists,null);
        List<MenuDO> treeGrid = menuTreeUtil.buildTreeGrid();
        model.addAttribute("menuLists",treeGrid);

        return "index";
    }

    /**
     * 系统管理员的默认首页
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/main", method = RequestMethod.GET)
    public String defaultMain(){
        return "content/default";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/test", method = RequestMethod.POST)
    public JobResponseDTO test(@RequestBody JobRequestDTO jobRequestDTO){
        logger.info("请求参数：{}", jobRequestDTO);
        JobResponseDTO jobResponseDTO = new JobResponseDTO();
        jobResponseDTO.setReqSequence(jobRequestDTO.getReqSequence());
        jobResponseDTO.setJobNameCode(jobRequestDTO.getJobNameCode());
        jobResponseDTO.setJobGroupCode(jobRequestDTO.getJobGroupCode());
        jobResponseDTO.setCode(1);
        return  jobResponseDTO;
    }
}
