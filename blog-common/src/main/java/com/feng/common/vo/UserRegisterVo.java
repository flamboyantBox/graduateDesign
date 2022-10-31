package com.feng.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 10/31/2022 3:20 PM
 */
@Data
@ApiModel(description = "用户注册")
public class UserRegisterVo {
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("注册账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("注册密码")
    private String password;

    @ApiModelProperty("用户人脸信息")
    private String faceInfo;
}
