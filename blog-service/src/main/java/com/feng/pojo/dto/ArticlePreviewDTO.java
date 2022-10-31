package com.feng.pojo.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.Feng
 * @date 9/21/2022 4:21 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewDTO {

    /**
     * 文章id
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

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;
}