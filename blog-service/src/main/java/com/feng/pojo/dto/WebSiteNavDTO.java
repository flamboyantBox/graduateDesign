package com.feng.pojo.dto;

/**
 * @author Mr.Feng
 * @date 9/8/2022 4:17 PM
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 网站导航列表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSiteNavDTO {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 网站名称
     */
    private String webSiteName;

    /**
     * 网站链接
     */
    private String webSiteUrl;

    /**
     * 网站图标链接
     */
    private String webSiteIcon;

    /**
     * 网站标签分类id
     */
    private Integer tagsId;

    /**
     * 网站标签
     */
    private String tagsName;

    /**
     * 网站描述
     */
    private String webSiteDesc;

    /**
     * 网站排序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
