package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.MessageBackDTO;
import com.feng.pojo.dto.MessageDTO;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MessageVo;
import com.feng.pojo.vo.ReviewVo;
import com.feng.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/message/core")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "添加留言")
    @PostMapping("addMessage")
    public R saveMessage(@Valid @RequestBody MessageVo messageVo) {
        messageService.saveMessage(messageVo);
        return R.ok();
    }

    @ApiOperation(value = "查看留言列表")
    @GetMapping("/messageList")
    public R messageList() {
        List<MessageDTO> messageDTOList = messageService.messageList();
        return R.ok().data("messageList", messageDTOList);
    }

    @ApiOperation(value = "查看后台留言列表")
    @PostMapping("messageBackList")
    public R messageBackList(@RequestBody ConditionVo condition) {
        List<MessageBackDTO> messageBackList = messageService.messageBackList(condition);
        return R.ok().data("messageBackList", messageBackList);
    }

    @ApiOperation(value = "审核留言")
    @PutMapping("messageReview")
    public R messagesReview(@Valid @RequestBody ReviewVo reviewVo) {
        messageService.messagesReview(reviewVo);
        return R.ok();
    }

    @ApiOperation(value = "删除留言")
    @DeleteMapping("messageDelete")
    public R messageDelete(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return R.ok();
    }
}

