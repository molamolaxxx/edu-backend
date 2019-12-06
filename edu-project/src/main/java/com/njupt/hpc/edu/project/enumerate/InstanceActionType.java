package com.njupt.hpc.edu.project.enumerate;

/**
 * 枚举实例发送action
 */
public enum InstanceActionType {
    /**
     * request
     */
    // 开始实例
    START(101,"开始执行实例"),
    // 停止实例
    STOP(102,"停止执行实例"),
    // 获取实例运行的信息（包括进度，部分运行日志）
    INFO(103,"获取实例执行的信息"),
    RESULT(104,"查看评估结果"),

    /**
     * response
     */
    NORMAL(104,"针对应用端的响应"),
    ERROR(105,"实例运行失败通知"),
    FINISH(106,"实例运行完毕通知");
    private Integer actionCode;

    private String actionDesc;

    InstanceActionType(Integer actionCode, String actionDesc) {
        this.actionCode = actionCode;
        this.actionDesc = actionDesc;
    }}
