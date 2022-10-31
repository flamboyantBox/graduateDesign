package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr.Feng
 * @date 9/24/2022 4:33 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePaginationDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

}
