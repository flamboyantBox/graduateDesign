package com.feng.websocket.client;

import com.feng.websocket.pojo.entity.ChatRecord;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 10/20/2022 11:26 PM
 */
@FeignClient(value = "service-blog", path = "/chatRecord/core")
public interface ChatRecordClient {
    @GetMapping("chatRecordList")
    List<ChatRecord> chatRecordList();

    @PostMapping("saveChatRecord")
    void saveChatRecord(@RequestBody ChatRecord chatRecord);

    @GetMapping("deleteChatRecord")
    void deleteChatRecord(@RequestParam("messageId") Integer messageId);
}
