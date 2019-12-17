package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * 枚举实例发送action
 */
@Getter
public enum InstanceActionType {

    // 开始实例
    START(101,"开始执行实例"),
    // 停止实例
    STOP(102,"停止执行实例"),
    // 获取实例运行的信息（包括进度，部分运行日志）
    INFO(103,"获取实例执行的信息"),
    RESULT(104,"查看评估结果"),

    /**
     * 算法模块主动响应
     */
    ERROR(105,"实例运行失败通知"),
    FINISH(106,"实例运行完毕通知");


    // const
    public static final int _START = 101;
    public static final int _STOP = 102;
    public static final int _INFO = 103;
    public static final int _RESULT = 104;
    public static final int _ERROR = 105;
    public static final int _FINISH = 106;

    private Integer actionCode;

    private String actionDesc;

    InstanceActionType(Integer actionCode, String actionDesc) {
        this.actionCode = actionCode;
        this.actionDesc = actionDesc;
    }}
