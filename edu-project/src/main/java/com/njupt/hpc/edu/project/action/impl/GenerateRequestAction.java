package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.aware.EduApplicationAware;
import com.njupt.hpc.edu.common.sys.DataConfig;
import com.njupt.hpc.edu.project.action.RequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.impl.PmsDataServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块requestAction
 * @date : 2019-12-07 00:29
 **/
public class GenerateRequestAction extends RequestAction {

    // 因为无法自动注入，所以通过aware获取bean
    private DataConfig dataConfig = (DataConfig) EduApplicationAware
            .getApplicationContext().getBean("dataConfig");

    private PmsDataService dataService = (PmsDataServiceImpl) EduApplicationAware
            .getApplicationContext().getBean("pmsDataServiceImpl");

    public GenerateRequestAction(String actionId, PmsInstance instance,
                                 InstanceActionType actionType, String message) {
        this.setActionId(actionId);
        this.setInstanceTypeEnum(InstanceTypeEnum.GENERATE_EVALUATE);
        this.setInstance(instance);
        this.setActionType(actionType);
        this.setMessage(message);
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
        JSONObject json = createCommonHeader();
        Map dataMap = new HashMap();
        // 找到对应数据的具体信息
        PmsData instanceData = dataService.getById(this.getInstance().getDataId());
        dataMap.put("dataPath",instanceData.getDataPath());
        dataMap.put("dataType",instanceData.getDataType());
        json.put("data",dataMap);
        // 放置系统配置信息
        json.put("sysConfig","none");
        return json;
    }

    private JSONObject handleStop(){
        return JSON.parseObject("");
    }

    private JSONObject handleInfo(){
        return JSON.parseObject("");
    }

    /**
     * 创建公共头
     * @return
     */
    private JSONObject createCommonHeader(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actionId", this.getActionId());
        jsonObject.put("instanceId", this.getInstance().getId());
        jsonObject.put("actionTypeId", this.getActionType().getActionCode());
        jsonObject.put("actionTypeName", this.getActionType().getActionDesc());
        jsonObject.put("message",this.getMessage());
        return jsonObject;
    }
}
