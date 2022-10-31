package com.feng.pojo.dto;

import com.feng.pojo.vo.PageVo;
import com.feng.pojo.vo.WebsiteConfigVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 9/19/2022 4:14 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoDTO {

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVo websiteConfig;

    /**
     * 页面列表
     */
    private List<PageVo> pageList;
}