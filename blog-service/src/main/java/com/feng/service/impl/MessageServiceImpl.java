package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.common.util.BeanCopyUtils;
import com.feng.common.util.IpUtils;
import com.feng.pojo.dto.MessageBackDTO;
import com.feng.pojo.dto.MessageDTO;
import com.feng.pojo.entity.Message;
import com.feng.mapper.MessageMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MessageVo;
import com.feng.pojo.vo.ReviewVo;
import com.feng.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.WebsiteConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Resource
    private HttpServletRequest request;

    @Override
    public void saveMessage(MessageVo messageVo) {
        // 判断是否需要审核
        Integer isReview = websiteConfigService.getWebsiteConfig().getIsMessageReview();
        // 获取用户ip
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message message = Message.builder().build();
        BeanUtils.copyProperties(messageVo, message);
        message.setIpAddress(ipAddress);
        message.setIsReview(isReview);
        message.setIpSource(ipSource);
        baseMapper.insert(message);
    }

    @Override
    public List<MessageDTO> messageList() {
        // 查询留言列表
        List<Message> messageList = baseMapper.selectList(new LambdaQueryWrapper<Message>()
                .eq(Message::getIsReview, 1));
        return BeanCopyUtils.copyList(messageList, MessageDTO.class);
    }

    @Override
    public List<MessageBackDTO> messageBackList(ConditionVo condition) {
        // 查询留言列表
        List<Message> messageList = this.list(new LambdaQueryWrapper<Message>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Message::getNickname, condition.getKeywords())
                .eq(Objects.nonNull(condition.getIsReview()), Message::getIsReview, condition.getIsReview())
                .orderByDesc(Message::getId));
        // 转换DTO
        return BeanCopyUtils.copyList(messageList, MessageBackDTO.class);
    }

    @Override
    public void messagesReview(ReviewVo reviewVo) {
        // 修改留言审核状态
        List<Message> messageList = reviewVo.getIdList().stream().map(item -> Message.builder()
                .id(item)
                .isReview(reviewVo.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }
}
