package com.itzf.springbootshiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局shiro配置
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 注入SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 需要登录的接口，如果访问某个接口，需要登录却没登录，则调用此接口(如果不是前后端分离，则跳转页面)
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

        // 没有权限，未授权就会调用此方法， 先验证登录-》再验证是否有权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");

        //登录成功，跳转url，如果前后端分离，则没这个调用
        shiroFilterFactoryBean.setSuccessUrl("/");

        // 自定义角色、权限过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleFilter", new CustomRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器路径
        // 坑一，部分路径无法进行拦截，时有时无；因为同学使用的是hashmap, 无序的，应该改为LinkedHashMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");

        // 匿名可以访问，也是就游客模式
        filterChainDefinitionMap.put("/pub/**", "anon");

        // 登录用户才可以访问
        filterChainDefinitionMap.put("/authc", "authc");

        //管理员角色才可以访问
//        filterChainDefinitionMap.put("/admin/**","roles[admin,root]");
        filterChainDefinitionMap.put("/admin/**","roleFilter[admin,root]");

        //有编辑权限才可以访问
        filterChainDefinitionMap.put("/video/update","perms[video_update]");

        //坑二: 过滤链是顺序执行，从上而下，一般讲/** 放到最下面
        //authc : url定义必须通过认证才可以访问
        //anon  : url可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        return shiroFilterFactoryBean;
    }

    /**
     * securityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();

        // 设置密码校验方式
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        return customRealm;
    }

    /**
     * 密码加密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        // 加密方式md5
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密两次
        hashedCredentialsMatcher.setHashIterations(2);
        //

        return hashedCredentialsMatcher;
    }

    /**
     * SessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 配置redisManager
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost:6379");
        // 密码验证
        redisManager.setPassword("123456");
        return redisManager;
    }

    /**
     * 配置具体cache实现类
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        // 设置redis key的过期时间 单位：/s
        // redisCacheManager.setExpire(20);
        return redisCacheManager;
    }

    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        // 设置redis key的过期时间 单位：/s
        // redisSessionDAO.setExpire(30);

        // 自定义sessionId
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }

    /**
     * 管理shiro一些bean的生命周期 即bean初始化 与销毁
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 加入注解的使用，不加入这个AOP注解不生效(shiro的注解 例如 @RequiresGuest)
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 用来扫描上下文寻找所有的Advistor(通知器), 将符合条件的Advisor应用到切入点的Bean中，
     * 需要在LifecycleBeanPostProcessor创建后才可以创建
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
