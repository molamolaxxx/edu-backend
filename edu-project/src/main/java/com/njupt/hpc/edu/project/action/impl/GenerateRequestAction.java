package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.project.action.RequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块requestAction
 * @date : 2019-12-07 00:29
 **/
public class GenerateRequestAction extends RequestAction {

    public GenerateRequestAction(String actionId, String instanceId, InstanceActionType actionType) {
        this.setActionId(actionId);
        this.setInstanceTypeEnum(InstanceTypeEnum.GENERATE_EVALUATE);
        this.setInstanceId(instanceId);
        this.setActionType(actionType);
    }

    @Override
    public JSON parse() {
        JSONObject result = null;
        switch (this.getActionType().getActionCode()){
            // 将start的请求转化成json
            case InstanceActionType._START : {
                result = handleStart();
                break;
            }
            case InstanceActionType._STOP : {
                result = handleStop();
                break;
            }
            case InstanceActionType._INFO : {
                result = handleInfo();
                break;
            }
        }
        return result;
    }

    private JSONObject handleStart(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        return jsonObject;
    }

    private JSONObject handleStop(){
        return JSON.parseObject("");
    }

    private JSONObject handleInfo(){
        return JSON.parseObject("");
    }
}
