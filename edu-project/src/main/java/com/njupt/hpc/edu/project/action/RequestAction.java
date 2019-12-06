package com.njupt.hpc.edu.project.action;

import com.alibaba.fastjson.JSON;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;

import java.util.Map;

public abstract class RequestAction {

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
     * 一些参数
     */
    private Map<String, Object> param;

    /**
     * 将对象转化成json文件
     * @return
     */
    protected abstract JSON parse(RequestAction action);


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

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
