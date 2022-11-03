package com.feng.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.Feng
 * @date 11/3/2022 6:09 PM
 */
@Getter
@AllArgsConstructor
public enum AddressEnum {
    /**
     * 未知
     */
    UNKNOWN(0, "未知"),
    /**
     * 省
     */
    PROVINCE(1, "省"),
    /**
     * 市
     */
    CITY(2, "市"),
    ;

    /**
     * 代码
     */
    private final Integer code;

    /**
     * 地区
     */
    private final String address;
}
