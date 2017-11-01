package com.wu.admin.web.controller.auth;

import com.wu.admin.base.Pagination;
import com.wu.admin.pojo.auth.dto.ResponseDTO;
import com.wu.admin.pojo.auth.entity.AdminDO;
import com.wu.admin.service.auth.AdminService;
import com.wu.admin.service.auth.RoleService;
import com.wu.admin.web.ao.AdminAddAO;
import com.wu.admin.web.ao.ChangePasswdAO;
import com.wu.admin.web.vo.auth.RoleOptionVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author : wubo
 *         Date : 2017/10/12
 *         Time : 下午2:56
 */
//需要授权登录之后才可访问。
@RequiresAuthentication
@Controller
public class AdminCtl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @RequiresPermissions("admin:index")
    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String index() {
        return "auth/admin_index";
    }

    @RequiresPermissions("admin:index")
    @ResponseBody
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public ResponseDTO list() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Integer pageSize = Integer.valueOf(Objects.nonNull(request.getParameter("pageSize")) ? request.getParameter("pageSize") : "20");
        Integer pageNumber = Integer.valueOf(Objects.nonNull(request.getParameter("pageIndex")) ? request.getParameter("pageIndex") : "1");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String reservation = request.getParameter("reservation");
        logger.info("查询条件为，userName:{},email:{},reservation:{}", userName, email, reservation);
        Map<String, Object> params = new HashMap();
        if (!StringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }
        if (!StringUtils.isEmpty(email)) {
            params.put("userEmail", email);
        }
        Pagination<AdminDO> pagination = adminService.pageAdminsByConditions(pageNumber, pageSize, params);
        return new ResponseDTO(0, "success", pagination);
    }

    @RequiresPermissions("admin:edit")
    @RequestMapping(value = "admin/toEdit", method = RequestMethod.GET)
    public String toEdit(String uid) {
        return "auth/admin_edit";
    }

    @RequiresPermissions("admin:edit")
    @RequestMapping(value = "admin/add", method = RequestMethod.GET)
    public String add(String uid, Model model) {
        model.addAttribute("roleOptions", listRoleOptions());
        return "auth/admin_add";
    }

    @RequiresPermissions("admin:edit")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO save(@RequestBody AdminAddAO ao) {
        logger.info("请求参数: {}", ao);
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        ao.setSalt(salt);
        adminService.addAdmin(ao);

        return new ResponseDTO(1, "success", null);
    }

    @RequiresPermissions("admin:edit")
    @RequestMapping(value = "/admin", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseDTO edit(@RequestBody ChangePasswdAO ao) {
        logger.info("请求参数: {}", ao);
        adminService.editPassword(ao);
        return new ResponseDTO(1, "success", null);
    }

    private List<RoleOptionVO> listRoleOptions() {
        return roleService.listRoleOptions();
    }
}
