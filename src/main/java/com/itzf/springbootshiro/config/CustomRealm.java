package com.itzf.springbootshiro.config;

import com.itzf.springbootshiro.domain.Permission;
import com.itzf.springbootshiro.domain.Role;
import com.itzf.springbootshiro.domain.User;
import com.itzf.springbootshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义数据域realm
 *      - 自定义登陆验证信息   doGetAuthenticationInfo
 *      - 自定义授权信息       doGetAuthorizationInfo
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public class CustomRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findUserInfoByUserName(username);
        List<Role> roleList = user.getRoleList();
        List<String> roleNameList = new ArrayList<>();
        List<String> permNameList = new ArrayList<>();

        for (Role role : roleList) {
            roleNameList.add(role.getName());

            List<Permission> permList = role.getPermList();
            for(Permission permission : permList) {
                if(permission != null) {
                    permNameList.add(permission.getName());
                }
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleNameList);
        simpleAuthorizationInfo.addStringPermissions(permNameList);
        return simpleAuthorizationInfo;
    }

    /**
     * 登陆校验
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token获取用户信息，token代表用户输入
        String username = (String) token.getPrincipal();
        User user = userService.findSimpleUserInfoByUsername(username);
        String pwd = user.getPassword();

        if(pwd == null || "".equals(pwd)){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, pwd, this.getClass().getName());
        return simpleAuthenticationInfo;
    }
}
