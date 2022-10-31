package com.feng.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Feng
 * @date 7/8/2022 9:47 AM
 */
@Data
public class R {
    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 单例模式
     */
    private R(){

    }

    /**返回结果成功*/
    public static R ok(){
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**返回结果失败*/
    public static R error(){
        R r = new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    /**设置特定的结果集*/
    public static R setResult(ResponseEnum responseEnum){
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }
}
