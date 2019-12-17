package com.njupt.hpc.edu.project.action;

import com.alibaba.fastjson.JSON;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;

public abstract class RequestAction {

    /**
     * 请求id，用来作为全局deferresult的key
     */
    private String actionId;

    /**
     * 实例的id
     */
    private String instanceId;

    /**
     * 实例的类型
     */
    private InstanceTypeEnum instanceTypeEnum;

    /**
     * 操作的类型
     */
    private InstanceActionType actionType;

    /**
     * 发送消息
     */
    private String message;

    /**
     * 将对象转化成json文件
     * @return
     */
    protected abstract JSON parse();

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId= actionId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InstanceTypeEnum getInstanceTypeEnum() {
        return instanceTypeEnum;
    }

    public void setInstanceTypeEnum(InstanceTypeEnum instanceTypeEnum) {
        this.instanceTypeEnum = instanceTypeEnum;
    }

    public InstanceActionType getActionType() {
        return actionType;
    }

    public void setActionType(InstanceActionType actionType) {
        this.actionType = actionType;
    }

}
