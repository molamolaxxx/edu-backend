package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.project.action.ResponseAction;

/**
 * @program: edu-backend
 * @description: 融合评价时的
 * @author: Su
 * @create: 2020-05-13 22:22
 **/
public class FusionResponseAction extends ResponseAction {
    @Override
    protected ResponseAction parse(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        this.setActionId((String) jsonObject.get("actionId"));
        this.setInstanceId((String)jsonObject.get("instanceId"));
        this.setInstanceId((String) jsonObject.get("actionTypeId"));
        return this;
    }
}
