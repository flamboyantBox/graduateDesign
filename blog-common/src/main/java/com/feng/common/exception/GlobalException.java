package com.feng.common.exception;

import com.feng.common.result.R;
import com.feng.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Mr.Feng
 * @date 7/8/2022 10:56 AM
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e){
        log.error(e.getMessage(), e);
        return R.error().message(ResponseEnum.GLOBAL_ERROR.getMessage()).code(ResponseEnum.GLOBAL_ERROR.getCode());
    }

    @ExceptionHandler(value = BlogException.class)
    public R handlerException(BlogException e){
        log.error(e.getMessage(), e);
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
