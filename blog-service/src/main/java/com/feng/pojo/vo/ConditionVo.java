package com.feng.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Mr.Feng
 * @date 8/8/2022 5:49 PM
 */
@Data
public class ConditionVo {

    /**
     * 页码
     */
    @ApiModelProperty(name = "current", value = "页码", dataType = "Long")
    private Long current;

    /**
     * 条数
     */
    @ApiModelProperty(name = "size", value = "条数", dataType = "Long")
    private Long size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;

    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer tagId;

    /**
     * 文章id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer articleId;

    /**
     * 相册id
     */
    @ApiModelProperty(name = "albumId", value = "相册id", dataType = "Integer")
    private Integer albumId;

    /**
     * 登录类型
     */
    @ApiModelProperty(name = "type", value = "登录类型", dataType = "Integer")
    private Integer loginType;

    /**
     * 类型
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;

    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "状态", dataType = "Integer")
    private Integer status;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "LocalDateTime")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "LocalDateTime")
    private LocalDateTime endTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @ApiModelProperty(name = "isReview", value = "是否删除", dataType = "Integer")
    private Integer isReview;
}
