package com.feng.websocket.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr.Feng
 * @date 10/20/2022 9:31 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageDTO {
    /**
     * 类型
     */
    private Integer type;

    /**
     * 数据
     */
    private Object data;
}
