package com.feng.service;

import com.feng.pojo.dto.MessageBackDTO;
import com.feng.pojo.dto.MessageDTO;
import com.feng.pojo.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MessageVo;
import com.feng.pojo.vo.ReviewVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface MessageService extends IService<Message> {

    void saveMessage(MessageVo messageVo);

    List<MessageDTO> messageList();

    List<MessageBackDTO> messageBackList(ConditionVo condition);

    void messagesReview(ReviewVo reviewVo);
}
