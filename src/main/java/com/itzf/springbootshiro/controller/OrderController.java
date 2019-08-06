package com.itzf.springbootshiro.controller;

import com.itzf.springbootshiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zf
 * @date 2019/8/6-22:43
 * @vesion CCS2
 */
@RestController
@RequestMapping("/authc")
public class OrderController {
    @RequestMapping("/video/record")
    public JsonData recordList() {
        Map<String ,String> recordMap = new HashMap<>();

        recordMap.put("SpringBoot入门到高级实战","第8章第1集");
        recordMap.put("Cloud微服务入门到高级实战","第4章第10集");
        recordMap.put("分布式缓存Redis","第10章第3集");
        return JsonData.buildSuccess(recordMap);
    }
}
