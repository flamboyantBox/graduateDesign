package com.feng.oss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 8/18/2022 2:25 PM
 */
@Data
@ApiModel(description = "图片地址信息")
public class RemovePhotoVo {
    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(name = "coverUrl", value = "照片地址", required = true, dataType = "String")
    private String photoUrl;
}
