package com.feng.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Mr.Feng
 * @date 7/8/2022 9:49 AM
 */
@Getter
@ToString
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS(200, "成功"),
    ERROR(-1, "服务器内部错误"),
    NOT_FOUND(404, "页面未找到"),
    GLOBAL_ERROR(101, "系统繁忙"),
    TEXT_NOTNULL(102, "文本不能为空"),
    PASSWORD_NULL_ERROR(103, "密码不能为空"),
    USERNAME_NULL_ERROR(104, "用户名不能为空"),
    LOGIN_AUTH_ERROR(105, "未登录"),
    LOGIN_USER_ERROR(106, "用户不存在"),
    LOGOUT_USER_ERROR(107, "用户已下线，请重新登录！"),
    FIND_ERROR(108, "查询失败"),
    UPLOAD_ERROR(109, "文件上传错误"),
    OLD_PASSWORD_ERROR(110, "旧密码不正确"),
    USERNAME_EXIST(111, "用户名已存在"),
    CATEGORY_EXIST(112, "分类已存在"),
    CATEGORY_EXIST_ARTICLE(113, "该分类下存在文章"),
    TAG_EXIST_ARTICLE(115, "该标签下存在文章"),
    EXIST_ALBUM(116, "相册名已存在"),
    LOGIN_PASSWORD_ERROR(117, "密码错误"),
    ROLE_MENU_ERROR(118, "菜单下有角色关联"),
    FACE_ERROR(119, "人脸错误"),
    ;

    private Integer code;
    private String message;
}
