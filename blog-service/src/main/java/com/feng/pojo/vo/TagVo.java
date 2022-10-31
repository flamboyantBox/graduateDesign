package com.feng.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 8/9/2022 3:30 PM
 */
@Data
public class TagVo {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "标签id", dataType = "Integer")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    @ApiModelProperty(name = "categoryName", value = "标签名", required = true, dataType = "String")
    private String tagName;
}
