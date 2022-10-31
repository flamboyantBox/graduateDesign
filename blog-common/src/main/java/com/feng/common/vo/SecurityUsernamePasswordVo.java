package com.feng.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 10/31/2022 9:14 PM
 */
@Data
@Builder
public class SecurityUsernamePasswordVo {
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("注册账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("注册密码")
    private String password;
}
