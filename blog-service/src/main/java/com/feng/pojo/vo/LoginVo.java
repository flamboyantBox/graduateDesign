package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Mr.Feng
 * @date 7/8/2022 4:32 PM
 */
@Data
@ApiModel("登陆对象")
public class LoginVo {
    @ApiModelProperty("登录账号")
    private String username;

    @ApiModelProperty("登陆密码")
    private String password;

    @ApiModelProperty("登陆类型 0管理员 1用户")
    private Integer loginType;
}
