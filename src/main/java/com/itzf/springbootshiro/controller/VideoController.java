package com.itzf.springbootshiro.controller;

import com.itzf.springbootshiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zf
 * @date 2019/8/6-22:54
 * @vesion CCS2
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @RequestMapping("/update")
    public JsonData updateVideo(){
        return JsonData.buildSuccess("video更新成功");
    }
}
