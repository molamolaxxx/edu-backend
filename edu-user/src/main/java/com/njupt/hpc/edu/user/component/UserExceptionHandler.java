package com.njupt.hpc.edu.user.component;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.exception.EduUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = EduUserException.class)
    @ResponseBody
    public CommonResult error(EduUserException e){
        e.printStackTrace();
        return CommonResult.failed("用户模块内部错误，错误内容:"+e.getMessage());
    }
}
