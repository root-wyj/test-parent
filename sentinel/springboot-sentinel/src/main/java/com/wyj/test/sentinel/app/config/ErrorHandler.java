package com.wyj.test.sentinel.app.config;

import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理 - 到达API请求后
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Throwable.class)
    public String errorHandlerOverJson(Exception e) {
        log.error("system error: ", e);
        return "fail. " + e.getMessage();
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public String errorHandlerUndeclared(UndeclaredThrowableException e) {
        log.error("undeclared error: ", e);
        if (e.getUndeclaredThrowable() instanceof  BlockException) {
            return blockExceptionHandler((BlockException) e.getUndeclaredThrowable());
        } else {
            return "undeclared e. " + e.getMessage();
        }
    }

    @ExceptionHandler(BlockException.class)
    public String blockExceptionHandler(BlockException e) {
        log.error("block error: ", e);
        return "block e. " + e.getClass().getName() + e.getMessage();
    }


}
