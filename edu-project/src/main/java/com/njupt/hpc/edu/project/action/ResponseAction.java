package com.njupt.hpc.edu.project.action;

/**
 * 应用模块与算法模块通信抽象接口，返回
 */
public abstract class ResponseAction {

    private String actionId;

    /**
     * 实例的id
     */
    private String instanceId;

    /**
     * 定义返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回的数据T
     */
    private String data;

    /**
     * 将json对象转化成ResponseAction对象
     * @return
     */
    protected abstract ResponseAction parse(String result);



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
