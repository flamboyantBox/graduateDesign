package com.feng.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式枚举
 *
 * @author bin
 * @date 2021/07/28
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    /**
     * 邮箱登录
     */
    EMAIL(1, "邮箱登录", "emailLogin"),
    /**
     * QQ登录
     */
    FACE(2, "人脸登录", "faceLogin"),
    ;

    /**
     * 登录方式
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 策略
     */
    private final String strategy;


}
