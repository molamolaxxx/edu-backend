package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.project.action.RequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2021-01-09 11:04
 **/
public class GenerateRequestAction extends RequestAction {

    public GenerateRequestAction(String actionId, PmsInstance instance,
                                 InstanceActionType actionType) {
        this.setActionId(actionId);
        this.setInstanceTypeEnum(InstanceTypeEnum.GENERATE);
        this.setInstance(instance);
        this.setActionType(actionType);
    }

    @Override
    protected JSON parse() {
        JSONObject result = null;
        switch (this.getActionType().getActionCode()){
            // 将start的请求转化成json
            case InstanceActionType._INVOKE : {
                result = handleInvoke();
                break;
            }
        }
        return result;
    }

    public JSONObject handleInvoke() {

    }
}
