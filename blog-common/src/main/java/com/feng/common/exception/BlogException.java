package com.feng.common.exception;

import com.feng.common.result.ResponseEnum;
import lombok.Data;

/**
 * @author Mr.Feng
 * @date 7/8/2022 1:45 PM
 */
@Data
public class BlogException extends RuntimeException {
    private Integer code;
    private String message;

    /**@param message 错误信息*/
    public BlogException(String message) {
        this.message = message;
    }

    /**@param code 错误码
     * @param message 错误信息*/
    public BlogException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**@param cause 错误码
     * @param message 错误信息
     * @param cause 错误原始对象*/
    public BlogException(Throwable cause, Integer code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    /**
     *
     * @param responseEnum 接收枚举类型
     */
    public BlogException(ResponseEnum responseEnum) {
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }

    /**
     *
     * @param responseEnum 接收枚举类型
     * @param cause 原始异常对象
     */
    public BlogException(ResponseEnum responseEnum, Throwable cause) {
        super(cause);
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }
}
