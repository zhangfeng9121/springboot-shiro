package com.itzf.springbootshiro.controller;

import com.itzf.springbootshiro.domain.User;
import com.itzf.springbootshiro.service.Impl.UserServiceImpl;
import com.itzf.springbootshiro.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
@RestController
@RequestMapping("/pub")
public class PublicController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{username}")
    public User findUserInfoByUsername(@PathVariable("username") String username) {
        User user = userService.findUserInfoByUserName(username);
        return user;
    }
}
