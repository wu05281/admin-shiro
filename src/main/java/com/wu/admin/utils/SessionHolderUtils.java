package com.wu.admin.utils;

import com.wu.admin.pojo.auth.entity.AdminDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 31 上午10:42
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class SessionHolderUtils {

    private HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    public static String getCurrentUid(){
        Subject subject = SecurityUtils.getSubject();
        AdminDO adminDO = (AdminDO) subject.getPrincipal();
        return adminDO.getUid();
    }

    public static String getCurrentUname(){
        Subject subject = SecurityUtils.getSubject();
        AdminDO adminDO = (AdminDO) subject.getPrincipal();
        return adminDO.getUserName();
    }
}
