package com.njupt.hpc.edu.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.cache.DeferredResultCache;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.DeferredResultUtil;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.action.impl.FusionRequestAction;
import com.njupt.hpc.edu.project.action.impl.GenerateRequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionResponseCode;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.mq.EduMQService;
import com.njupt.hpc.edu.project.service.AlgorithmService;
import com.njupt.hpc.edu.project.service.PmsActionService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法操作接口实现
 * @date : 2019-12-16 15:33
 **/
@Service
@Slf4j
public class PmsActionServiceImpl implements PmsActionService {

    @Resource
    private EduMQService mqService;

    @Resource
    private PmsInstanceService instanceService;

    @Resource
    private PmsDataService dataService;

    @Resource
    private AlgorithmService algorithmService;

    @Override
    public DeferredResult action(PmsInstance instance, InstanceActionType actionType) {
        if (null == instance) {
            throw new EduProjectException("实例不存在");
        }
        if (!algorithmService.isInstanceContainerOnline(instance.getType())) {
            log.warn("实例类型{}的算法容器不在线，操作【{}】执行失败", instance.getType(), actionType.getActionDesc());
            // 将该实例置为失败状态
            instanceService.updateInstanceState(instance.getId(), InstanceActionType._ERROR);
            return DeferredResultUtil.buildFailedResult("操作【"+actionType.getActionDesc()+"】执行失败，算法容器不在线");
        }
        // 构建actionId与deferResult
        String actionId = IdUtil.generateId("action");
        DeferredResult<CommonResult> result = DeferredResultUtil.build(actionId, "运行实例操作时,异步队列请求超时");
        // 如果是info消息
        if(!instance.getState().equals(InstanceStateEnum.RUNNING.getCode()) &&
                actionType.getActionCode().equals(InstanceActionType._INFO)){
            // 直接返回实例状态
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("state", instance.getState());
            result.setResult(CommonResult.success(resultMap));
            return result;
        }

        // 1.检查instance状态，数据是否存在、合法
        checkInstanceLegality(instance, actionType);
        checkDataLegality(instance.getDataId());

        // 2.构建对应的request（开始执行）
        String actionStr = getRequestActionByInstanceType(actionId, instance, actionType);
        log.info("action_detail:"+actionStr);

        // 3.向队列发送json化的request,将键值对存入缓存
        mqService.sendMessageToMQ(actionStr);
        DeferredResultCache.put(actionId, result);
        // 4.获取返回值，用于判断下一步的操作
        result.onCompletion(() -> {
            CommonResult commonResult = (CommonResult)result.getResult();
            JSONObject actionResponse = (JSONObject) commonResult.getData();
            if (actionResponse.get("code").equals(InstanceActionResponseCode.ACTION_SUCCESS.getCode())){
                // 设置实例时间状态与实例运行状态
                log.info("设置实例开始时间与实例运行状态");
                instanceService.updateInstanceState(instance.getId(), (String) actionResponse.get("actionTypeId"));
            }
            //若停止，则调用运行完成的操作
            if(actionResponse.get("actionTypeId").equals(InstanceActionType.STOP.getActionCode())){
                mqService.handlerFinish(instance.getId(), actionResponse);
            }
        });
        return result;
    }

    /**
     * 检查实例合法性
     * @return
     */
    public void checkInstanceLegality(PmsInstance instance, InstanceActionType actionType){
        if (!instance.getState().equals(InstanceStateEnum.READY.getCode()) &&
                actionType.getActionCode().equals(InstanceActionType._START)) {
            throw new EduProjectException("实例状态不合法:实例不可运行");
        }
        else if(!instance.getState().equals(InstanceStateEnum.RUNNING.getCode()) &&
                actionType.getActionCode().equals(InstanceActionType._STOP)){
            throw new EduProjectException("实例状态不合法:实例不可停止");
        }
    }

    /**
     * 检查数据合法性
     * @return
     */
    public void checkDataLegality(String dataId){
        PmsData data = dataService.getById(dataId);
        if (null == data){
            throw new EduProjectException("实例操作失败:不存在数据记录");
        }
        if (data.getDataSize().equals("0")){
            throw new EduProjectException("实例操作失败:数据大小为0");
        }
        if (!new File(data.getDataPath()).exists()){
            throw new EduProjectException("实例操作失败:数据路径不存在");
        }
    }

    /**
     * 根据实例的类型构建发送的action
     * @param actionId 本次action的id
     * @param instance 操作的实例
     * @param type 操作的类型
     * @return
     */
    public String getRequestActionByInstanceType(String actionId, PmsInstance instance, InstanceActionType type){
        switch (instance.getType()){
            // 生成评价
            case InstanceTypeEnum._GENERATE_EVALUATE:{
                GenerateRequestAction action = new GenerateRequestAction(actionId, instance, type);
                return action.parse2String();
            }
            // 聚合评价
            case InstanceTypeEnum._FUSION_EVALUATE:{
                FusionRequestAction fusionRequestAction = new FusionRequestAction(actionId, instance, type);
                return fusionRequestAction.parse2String();
            }
        }
        throw new EduProjectException("没有符合的实例请求转换器");
    }
}
