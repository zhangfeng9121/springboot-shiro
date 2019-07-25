package com.itzf.springbootshiro.service;

import com.itzf.springbootshiro.domain.User;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public interface UserService {

    /**
     * 根据用户名称查询用户所有信息（角色、权限）
     * @param username
     * @return
     */
    User findUserInfoByUserName(String username);

    /**
     * 根据用户名称查询单个用户信息
     * @param username
     * @return
     */
    User findSimpleUserInfoByUsername(String username);

    /**
     * 根据用户Id查询单个用户信息
     * @param userId
     * @return
     */
    User findSimpleUserInfoById(int userId);
}
