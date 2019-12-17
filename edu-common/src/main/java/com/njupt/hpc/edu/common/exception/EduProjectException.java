package com.njupt.hpc.edu.common.exception;

import com.njupt.hpc.edu.common.api.ResultCode;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-16 15:29
 **/
public class EduProjectException extends RuntimeException{

    /**
     * 错误码
     */
    private ResultCode code;

    private String msg;

    public EduProjectException(String msg, ResultCode code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EduProjectException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = ResultCode.FAILED;
    }
}
