package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Mr.Feng
 * @date 8/8/2022 10:45 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类名
     */
    private LocalDateTime createTime;

    /**
     * 分类下的文章数量
     */
    private Integer articleCount;
}
