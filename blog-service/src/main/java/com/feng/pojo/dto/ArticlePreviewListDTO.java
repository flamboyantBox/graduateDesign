package com.feng.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 9/21/2022 11:03 PM
 */
@Data
public class ArticlePreviewListDTO {
    /**
     * 整个排序好的文章
     */
    private List<ArticlePreviewDTO> articlePreviewDTOList;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;
}
