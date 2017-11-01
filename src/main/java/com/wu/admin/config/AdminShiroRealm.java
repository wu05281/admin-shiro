package com.wu.admin.config;

import com.wu.admin.pojo.auth.entity.AdminDO;
import com.wu.admin.pojo.auth.entity.MenuDO;
import com.wu.admin.service.auth.AdminService;
import com.wu.admin.service.auth.RoleMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:17
 */
public class AdminShiroRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;


    @Resource
    private RoleMenuService roleMenuService;
    /**
     * 认证信息.(身份验证)
     * :
     * Authentication 是用来验证用户身份
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取用户的输入的账号.
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = (String)token.getUsername();
        String pwd = String.valueOf(token.getPassword());

        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        AdminDO userInfo = adminService.findByUserName(username);
        if(userInfo == null){
            throw new UnknownAccountException();
        }
        if(!userInfo.getState()) {
            throw new LockedAccountException(); //帐号锁定
        }

       /*
        * 生成AuthenticationInfo信息，交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
        * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
        * 在组装SimpleAuthenticationInfo信息时，需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配
        */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密文密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                userInfo.getUserName()  //realm name
        );

        // 当验证都通过后，把用户信息放在session里
        //Session session = SecurityUtils.getSubject().getSession();
        return authenticationInfo;
    }



    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException{
       /*
        * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
        * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
        * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
        * 缓存过期之后会再次执行。
        */
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AdminDO userInfo  = (AdminDO)principals.getPrimaryPrincipal();
        Set<String> set = new HashSet<String>();
        set.add(userInfo.getRole().getRoleId());
        authorizationInfo.setRoles(set);

        Set<String> menus = roleMenuService.getMenuCodesByRoleId(userInfo.getRole().getRoleId());
        authorizationInfo.addStringPermissions(menus);

        return authorizationInfo;
    }

    /**
     * 清除缓存
     */
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


    /**
     * 将权限对象中的权限code取出.
     * @param code 权限对象
     * @return
     */
    public Set<String> getStringCode(Set<MenuDO> code){
        Set<String> stringPermissions = new HashSet<String>();
        if(code != null){
            for(MenuDO m : code) {
                stringPermissions.add(m.getMenuCode());
            }
        }
        return stringPermissions;
    }
}
