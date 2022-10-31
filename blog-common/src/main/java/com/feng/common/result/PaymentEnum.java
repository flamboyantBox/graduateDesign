package com.feng.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentEnum {
    /**
     * 微信支付
     */
    WECHAT(1, "微信"),
    /**
     * 支付宝支付
     */
    ALIPAY(2, "支付宝");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;
}
