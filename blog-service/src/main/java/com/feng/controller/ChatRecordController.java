package com.feng.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.common.result.R;
import com.feng.common.util.BeanCopyUtils;
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

    @ApiOperation(value = "查看后台历史聊天记录列表")
    @GetMapping("chatList")
    public R getChatList() {
        //查询聊天记录列表
        List<ChatRecord> chatList = chatRecordService.list(new LambdaQueryWrapper<ChatRecord>()
                .orderByDesc(ChatRecord::getCreateTime));
        // 转换DTO
        List<ChatRecord> chatRecordList = BeanCopyUtils.copyList(chatList, ChatRecord.class);
        return R.ok().data("chatRecordList", chatRecordList);
    }

    @ApiOperation(value = "删除历史聊天记录")
    @DeleteMapping("chatDelete")
    public R deleteChatRecord(@RequestBody List<Integer> chatRecordIdList){
        chatRecordService.removeByIds(chatRecordIdList);
        return R.ok();
    }
}

