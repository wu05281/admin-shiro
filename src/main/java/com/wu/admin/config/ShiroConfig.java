package com.wu.admin.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Description : Created by intelliJ IDEA
 * @author  : wubo
 * @version : 1.0.0
 * create time : 2017/10/10 下午5:15
 */
@Configuration
public class ShiroConfig {

    /**
     * 后台身份认证realm;
     *
     */
    @Bean(name = "adminShiroRealm")
    public AdminShiroRealm adminShiroRealm() {
        AdminShiroRealm adminShiroRealm = new AdminShiroRealm();
        adminShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return adminShiroRealm;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     */
    @Bean(name = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(adminShiroRealm());
        // 自定义缓存实现 使用ehCache
        securityManager.setCacheManager(ehCacheManager());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    /**
     * Shiro默认提供了三种 AuthenticationStrategy 实现：
     * AtLeastOneSuccessfulStrategy ：其中一个通过则成功。
     * FirstSuccessfulStrategy ：其中一个通过则成功，但只返回第一个通过的Realm提供的验证信息。
     * AllSuccessfulStrategy ：凡是配置到应用中的Realm都必须全部通过。
     * authenticationStrategy
     *
     */
    @Bean(name = "authenticationStrategy")
    public AuthenticationStrategy authenticationStrategy() {
        return new FirstSuccessfulStrategy();
    }

    /**
     * @see DefaultWebSessionManager
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setSessionDAO(new CustomSessionDAO());
        sessionManager.setCacheManager(ehCacheManager());
        //单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(3600000 * 12);
        //3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(3600000 * 12);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("WEBID");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     *
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }


    /**
    * 自定义错误页面
    */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        simpleMappingExceptionResolver.setDefaultErrorView("/login");
        Properties mappings = new Properties();
        mappings.setProperty("org.apache.shiro.authc.UnknownAccountException","/403");
        mappings.setProperty("org.apache.shiro.authc.IncorrectCredentialsException","/403");
        mappings.setProperty("org.apache.shiro.authc.LockedAccountException","/403");
        mappings.setProperty("org.apache.shiro.authc.ExcessiveAttemptsException","/403");
        mappings.setProperty("org.apache.shiro.authc.AuthenticationException","/403");
        mappings.setProperty("org.apache.shiro.authz.UnauthorizedException","/login");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        simpleMappingExceptionResolver.setExcludedExceptions(HibernateException.class);
        return simpleMappingExceptionResolver;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     * anon（匿名）  org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc（身份验证）       org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic（http基本验证）    org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * logout（退出）        org.apache.shiro.web.filter.authc.LogoutFilter
     * noSessionCreation（不创建session） org.apache.shiro.web.filter.session.NoSessionCreationFilter
     * perms(许可验证)  org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
     * port（端口验证）   org.apache.shiro.web.filter.authz.PortFilter
     * rest  (rest方面)  org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles（权限验证）  org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * ssl （ssl方面）   org.apache.shiro.web.filter.authz.SslFilter
     * member （用户方面）  org.apache.shiro.web.filter.authc.UserFilter
     * user  表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     */

    @Bean(name = "shirFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/toLogin", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");

        //anthc：authc filter 监听，不登陆不能访问
        filterChainDefinitionMap.put("/**", "authc");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
