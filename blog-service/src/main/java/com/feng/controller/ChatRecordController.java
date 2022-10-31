package com.feng.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.result.R;
import com.feng.pojo.dto.ArticleDTO;
import com.feng.pojo.entity.ChatRecord;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.ChatRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/chatRecord/core")
public class ChatRecordController {
    @Autowired
    private ChatRecordService chatRecordService;

    @ApiOperation(value = "查看后台聊天记录")
    @GetMapping("chatRecordList")
    public List<ChatRecord> chatRecordList() {
        return chatRecordService.list(new LambdaQueryWrapper<ChatRecord>()
                .orderByAsc(ChatRecord::getCreateTime));
    }

    @ApiOperation(value = "保存聊天记录")
    @PostMapping("saveChatRecord")
    public void saveChatRecord(@RequestBody ChatRecord chatRecord) {
        chatRecordService.save(chatRecord);
    }

    @ApiOperation(value = "撤销聊天记录")
    @GetMapping("deleteChatRecord")
    public void deleteChatRecord(@RequestParam("messageId") Integer messageId) {
        chatRecordService.removeById(messageId);
    }
}

