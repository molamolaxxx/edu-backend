package com.njupt.hpc.edu.common.exception;

import com.njupt.hpc.edu.common.api.ResultCode;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-27 15:56
 **/
public class EduUserException extends RuntimeException {

    /**
     * 错误码
     */
    private ResultCode code;

    private String msg;

    public EduUserException(String msg, ResultCode code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EduUserException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = ResultCode.FAILED;
    }
}
