package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr.Feng
 * @date 11/8/2022 9:36 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAreaDTO {
    /**
     * 地区名
     */
    private String name;

    /**
     * 数量
     */
    private Long value;
}
