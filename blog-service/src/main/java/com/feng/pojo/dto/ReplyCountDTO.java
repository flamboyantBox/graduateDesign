package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr.Feng
 * @date 10/16/2022 2:24 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCountDTO {

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;

}
