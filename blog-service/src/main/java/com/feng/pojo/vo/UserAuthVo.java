package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Mr.Feng
 * @date 7/8/2022 4:53 PM
 */
@Data
@ApiModel(description = "用户信息对象")
public class UserAuthVo {
    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "用户ip地址")
    private String ip;

    @ApiModelProperty(value = "用户类型 1管理员 2用户 3测试")
    private Integer loginType;

    @ApiModelProperty(value = "JWT访问令牌")
    private String token;
}
