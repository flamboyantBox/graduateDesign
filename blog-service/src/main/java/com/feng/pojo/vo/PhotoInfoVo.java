package com.feng.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Mr.Feng
 * @date 8/18/2022 3:43 PM
 */
@Data
public class PhotoInfoVo {
    /**
     * 照片id
     */
    @NotNull(message = "照片id不能为空")
    @ApiModelProperty(name = "id", value = "照片id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 照片名
     */
    @NotBlank(message = "照片名不能为空")
    @ApiModelProperty(name = "photoName", value = "照片名", required = true, dataType = "String")
    private String photoName;

    /**
     * 照片描述
     */
    @ApiModelProperty(name = "photoDesc", value = "照片描述", dataType = "String")
    private String photoDesc;
}
