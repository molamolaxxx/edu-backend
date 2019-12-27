package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * 模块通信过程中的返回码
 */
@Getter
public enum InstanceActionResponseCode {
    ACTION_SUCCESS("201","操作成功"),
    ACTION_FAILED("202","操作失败");

    private String code;

    private String msg;

    InstanceActionResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
