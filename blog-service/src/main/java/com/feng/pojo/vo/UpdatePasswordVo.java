package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 7/15/2022 9:30 AM
 */
@Data
@ApiModel(description = "修改管理员密码")
public class UpdatePasswordVo {

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(name = "oldPassword", value = "旧密码", required = true, dataType = "String")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(name = "newPassword", value = "新密码", required = true, dataType = "String")
    private String newPassword;
}
