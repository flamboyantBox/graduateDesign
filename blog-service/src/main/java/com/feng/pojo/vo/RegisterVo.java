package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 7/15/2022 10:57 AM
 */
@Data
@ApiModel("注册对象")
public class RegisterVo {
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("注册账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("注册密码")
    private String password;

    @ApiModelProperty("注册类型 0管理员 1用户")
    private Integer loginType;
}
