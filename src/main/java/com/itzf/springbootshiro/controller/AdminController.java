package com.itzf.springbootshiro.controller;

import com.itzf.springbootshiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zf
 * @date 2019/8/6-22:47
 * @vesion CCS2
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/video/order")
    public JsonData findMyOrder() {
        Map<String ,String> recordMap = new HashMap<>();

        recordMap.put("SpringBoot入门到高级实战","300元");
        recordMap.put("Cloud微服务入门到高级实战","877元");
        recordMap.put("分布式缓存Redis","990元");

        return JsonData.buildSuccess(recordMap);
    }
}
