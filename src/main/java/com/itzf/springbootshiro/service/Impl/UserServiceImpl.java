package com.itzf.springbootshiro.service.Impl;

import com.itzf.springbootshiro.dao.RoleMapper;
import com.itzf.springbootshiro.dao.UserMapper;
import com.itzf.springbootshiro.domain.Role;
import com.itzf.springbootshiro.domain.User;
import com.itzf.springbootshiro.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;
    @Override
    public User findUserInfoByUserName(String username) {
        User user = userMapper.findByUsername(username);
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findSimpleUserInfoById(int userId) {
        return userMapper.findById(userId);
    }
}
