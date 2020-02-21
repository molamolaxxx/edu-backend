package com.njupt.hpc.edu.project.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.aware.EduApplicationAware;
import com.njupt.hpc.edu.common.sys.DataConfig;
import com.njupt.hpc.edu.common.sys.GenerateConfig;
import com.njupt.hpc.edu.project.action.RequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.impl.PmsDataServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 生成模块requestAction
 * @date : 2019-12-07 00:29
 **/
@Slf4j
public class GenerateRequestAction extends RequestAction {

    // 因为无法自动注入，所以通过aware获取bean
    private DataConfig dataConfig = (DataConfig) EduApplicationAware
            .getApplicationContext().getBean("dataConfig");

    private PmsDataService dataService = (PmsDataServiceImpl) EduApplicationAware
            .getApplicationContext().getBean("pmsDataServiceImpl");

    private GenerateConfig generateConfig = (GenerateConfig) EduApplicationAware
            .getApplicationContext().getBean("generateConfig");

    public GenerateRequestAction(String actionId, PmsInstance instance,
                                 InstanceActionType actionType) {
        this.setActionId(actionId);
        this.setInstanceTypeEnum(InstanceTypeEnum.GENERATE_EVALUATE);
        this.setInstance(instance);
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

    public String parse2String(){
        return this.parse().toString();
    }

    private JSONObject handleStart(){
        JSONObject json = createCommonHeader();
        Map dataMap = new HashMap();
        // 找到对应数据的具体信息
        PmsData instanceData = dataService.getById(this.getInstance().getDataId());
        dataMap.put("dataPath",instanceData.getDataPath());
        dataMap.put("dataType",instanceData.getDataType());
        // 放置数据的信息
        json.put("data",dataMap);
        // 从实例中获取相应的实例配置，如果获取不到，沿用默认的系统配置
        // 放置系统配置信息（生成模块的配置）
        try {
            generateConfig.checkConfigInInstance(this.getInstance().getConfig());
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("instanceId:"+ getInstance().getId() +"配置出错");
            json.put("sysConfig", generateConfig.parseToJsonObject());
            return json;
        }
        json.put("sysConfig", generateConfig.parseToJsonObject());
        return json;
    }

    private JSONObject handleStop(){
        return createCommonHeader();
    }

    private JSONObject handleInfo(){
        return createCommonHeader();
    }

    /**
     * 创建公共头
     * @return
     */
    private JSONObject createCommonHeader(){
        JSONObject jsonObject = new JSONObject();
        // action的id与实例id
        jsonObject.put("actionId", this.getActionId());
        jsonObject.put("instanceId", this.getInstance().getId());
        // action类型的id与name
        jsonObject.put("actionTypeId", this.getActionType().getActionCode());
        jsonObject.put("actionTypeName", this.getActionType().getActionDesc());
        // 实例类型的id与name
        jsonObject.put("instanceTypeId", this.getInstanceTypeEnum().getCode());
        jsonObject.put("instanceTypeName", this.getInstanceTypeEnum().getDesc());

        return jsonObject;
    }
}
