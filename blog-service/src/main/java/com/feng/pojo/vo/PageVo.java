package com.feng.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 8/30/2022 2:03 PM
 */
@Data
public class PageVo {
    /**
     * 页面id
     */
    @ApiModelProperty(name = "id", value = "页面id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名称不能为空")
    @ApiModelProperty(name = "pageName", value = "页面名称", required = true, dataType = "String")
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空")
    @ApiModelProperty(name = "pageLabel", value = "页面标签", required = true, dataType = "String")
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空")
    @ApiModelProperty(name = "pageCover", value = "页面封面", required = true, dataType = "String")
    private String pageCover;
}
