package com.feng.websocket.pojo.dto;

import com.feng.websocket.pojo.entity.ChatRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 10/21/2022 2:58 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRecordDTO {
    /**
     * 聊天记录
     */
    private List<ChatRecord> chatRecordList;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;
}
