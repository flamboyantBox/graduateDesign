package com.feng.oss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 8/12/2022 9:58 AM
 */
@Data
@ApiModel(description = "上传图片地址信息")
public class UploadCoverVo {
    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(name = "coverUrl", value = "封面地址", required = true, dataType = "String")
    private String coverUrl;
}
