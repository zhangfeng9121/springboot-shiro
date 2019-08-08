package com.itzf.springbootshiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * @AUTHOR ZF
 * @DATE 2019/8/8
 */
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        Subject subject = getSubject(request, response);
        // 获取当前访问路径所需要的角色集合
        String[] rolesArray = (String[]) mappedValue;

        // 没有角色限制，有权限访问
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        // 当前subject是rolesArray中的任何一个，则有权限访问
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for(String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
