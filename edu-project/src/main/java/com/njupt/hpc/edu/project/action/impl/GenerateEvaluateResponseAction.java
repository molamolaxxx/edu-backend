package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.project.action.ResponseAction;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-04-15 11:18
 **/
public class GenerateResponseAction extends ResponseAction {
    @Override
    protected ResponseAction parse(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        this.setActionId((String) jsonObject.get("actionId"));
        this.setInstanceId((String)jsonObject.get("instanceId"));
        this.setInstanceId((String) jsonObject.get("actionTypeId"));
        return this;
    }
}
