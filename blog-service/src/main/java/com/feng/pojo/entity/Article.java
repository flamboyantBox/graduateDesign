package com.feng.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_article")
@ApiModel(value="Article对象", description="")
@Builder
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作者")
    private Integer userId;

    @ApiModelProperty(value = "文章分类")
    private Integer categoryId;

    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    @ApiModelProperty(value = "标题")
    private String articleTitle;

    @ApiModelProperty(value = "内容")
    private String articleContent;

    @ApiModelProperty(value = "文章类型 1原创 2转载 3翻译")
    private Integer type;

    @ApiModelProperty(value = "原文链接")
    private String originalUrl;

    @ApiModelProperty(value = "是否置顶 0否 1是")
    @TableField("is_top")
    private Integer top;

    @ApiModelProperty(value = "是否删除  0否 1是")
    @TableField("is_delete")
    private Integer deleted;

    @ApiModelProperty(value = "状态值 1公开 2私密 3评论可见")
    private Integer status;

    @ApiModelProperty(value = "发表时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
