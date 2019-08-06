package com.itzf.springbootshiro.controller;

import com.itzf.springbootshiro.domain.JsonData;
import com.itzf.springbootshiro.domain.User;
import com.itzf.springbootshiro.service.Impl.UserServiceImpl;
import com.itzf.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/need_login")
    public JsonData needLogin() {
        return JsonData.buildError("温馨提示：请使用对应的账号登录", -2);
    }

    @GetMapping("not_permit")
    public JsonData notPermit() {
        return JsonData.buildError("温馨提示：无权访问",-3);
    }

    @RequestMapping("/index")
    public JsonData index() {
        List<String> videoList = new ArrayList<>();
        videoList.add("Mysql零基础入门到实战 数据库教程");
        videoList.add("Redis高并发高可用集群百万级秒杀实战");
        videoList.add("Zookeeper+Dubbo视频教程 微服务教程分布式教程");
        videoList.add("2019年新版本RocketMQ4.X教程消息队列教程");
        videoList.add("微服务SpringCloud+Docker入门到高级实战");
        return JsonData.buildSuccess(videoList);
    }

    @PostMapping("login")
    public JsonData userLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> resMap = new HashMap<>();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(usernamePasswordToken);
            resMap.put("msg", "登陆成功");
            resMap.put("session_id", subject.getSession().getId());
            return JsonData.buildSuccess(resMap);
        }catch (Exception e) {
            e.printStackTrace();
            return JsonData.buildError("温馨提示：账号或密码错误");
        }


    }
}
