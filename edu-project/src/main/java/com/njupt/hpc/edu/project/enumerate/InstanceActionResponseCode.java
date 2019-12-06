package com.njupt.hpc.edu.project.enumerate;

/**
 * 模块通信过程中的返回码
 */
public enum InstanceActionResponseCode {
    ACTION_SUCCESS(201,"操作成功"),
    ACTION_FAILED(202,"操作失败");

    private Integer code;

    private String msg;

    InstanceActionResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
