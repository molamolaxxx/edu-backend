package com.njupt.hpc.edu.common.exception.handler;

import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduUserException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class EduExceptionHandler {

    @ExceptionHandler(value = EduUserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult error(EduUserException e, HttpServletResponse response){
        e.printStackTrace();
        return CommonResult.failed("用户模块内部错误", e.getMessage());
    }

    /**
     * 服务器内部错误
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult error(Exception e, HttpServletResponse response){
        e.printStackTrace();
        return CommonResult.failed("服务器内部错误", e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult error(AccessDeniedException e, HttpServletResponse response){
        e.printStackTrace();
        return CommonResult.forbidden(e.getMessage());
    }

}
