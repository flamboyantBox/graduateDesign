package com.feng.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 7/14/2022 2:37 PM
 */
@Data
@ApiModel(description = "用户信息对象")
public class UserInfoVo {

    @ApiModelProperty(name = "avatar", value = "头像", dataType = "String")
    private String avatar;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    @ApiModelProperty(name = "intro", value = "简介", dataType = "String")
    private String intro;

    @ApiModelProperty(name = "website", value = "博客网站", dataType = "String")
    private String webSite;
}
