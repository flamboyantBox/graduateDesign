package com.feng.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.websocket.client.ChatRecordClient;
import com.feng.websocket.pojo.entity.ChatRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 10/21/2022 12:54 AM
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ChatRecordClient chatRecordClient;

    @GetMapping("/result")
    public List<ChatRecord> test(){
         return chatRecordClient.chatRecordList();
    }
}
