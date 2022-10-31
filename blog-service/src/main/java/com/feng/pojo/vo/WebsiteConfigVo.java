package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Mr.Feng
 * @date 7/15/2022 4:23 PM
 */
@Data
@ApiModel(description = "网站配置")
public class WebsiteConfigVo {
    @ApiModelProperty(name = "websiteAvatar", value = "网站头像", required = true, dataType = "String")
    private String websiteAvatar;

    @ApiModelProperty(name = "websiteName", value = "网站名称", required = true, dataType = "String")
    private String websiteName;

    @ApiModelProperty(name = "websiteAuthor", value = "网站作者", required = true, dataType = "String")
    private String websiteAuthor;

    @ApiModelProperty(name = "websiteIntro", value = "网站介绍", required = true, dataType = "String")
    private String websiteIntro;

    @ApiModelProperty(name = "websiteNotice", value = "网站公告", required = true, dataType = "String")
    private String websiteNotice;

    @ApiModelProperty(name = "websiteCreateTime", value = "网站创建时间", required = true, dataType = "LocalDateTime")
    private String websiteCreateTime;

    @ApiModelProperty(name = "websiteRecordNo", value = "网站备案号", required = true, dataType = "String")
    private String websiteRecordNo;

    @ApiModelProperty(name = "socialLoginList", value = "社交登录列表", required = true, dataType = "List<String>")
    private List<String> socialLoginList;

    @ApiModelProperty(name = "socialUrlList", value = "社交URL列表", required = true, dataType = "List<String>")
    private List<String> socialUrlList;

    @ApiModelProperty(name = "qq", value = "qq", required = true, dataType = "String")
    private String qq;

    @ApiModelProperty(name = "github", value = "github", required = true, dataType = "String")
    private String github;

    @ApiModelProperty(name = "gitee", value = "gitee", required = true, dataType = "String")
    private String gitee;

    @ApiModelProperty(name = "v", value = "网站头像", required = true, dataType = "String")
    private String touristAvatar;

    @ApiModelProperty(name = "userAvatar", value = "游客头像", required = true, dataType = "String")
    private String userAvatar;

    @ApiModelProperty(name = "isCommentReview", value = "是否评论审核", required = true, dataType = "Integer")
    private Integer isCommentReview;

    @ApiModelProperty(name = "isMessageReview", value = "是否留言审核", required = true, dataType = "Integer")
    private Integer isMessageReview;

    @ApiModelProperty(name = "isEmailNotice", value = "是否邮箱通知", required = true, dataType = "Integer")
    private Integer isEmailNotice;

    @ApiModelProperty(name = "isReward", value = "是否打赏", required = true, dataType = "Integer")
    private Integer isReward;

    @ApiModelProperty(name = "weiXinQRCode", value = "微信二维码", required = true, dataType = "String")
    private String weiXinQRCode;

    @ApiModelProperty(name = "alipayQRCode", value = "支付宝二维码", required = true, dataType = "String")
    private String alipayQRCode;

    @ApiModelProperty(name = "isChatRoom", value = "是否开启聊天室", required = true, dataType = "Integer")
    private Integer isChatRoom;

    @ApiModelProperty(name = "websocketUrl", value = "websocket地址", required = true, dataType = "String")
    private String websocketUrl;

    @ApiModelProperty(name = "isMusicPlayer", value = "是否开启音乐", required = true, dataType = "Integer")
    private Integer isMusicPlayer;
}
