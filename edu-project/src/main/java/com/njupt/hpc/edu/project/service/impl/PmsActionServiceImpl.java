package com.njupt.hpc.edu.project.service.impl;

import com.njupt.hpc.edu.common.cache.DeferredResultCache;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.DeferredResultUtil;
import com.njupt.hpc.edu.project.action.impl.GenerateRequestAction;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.InstanceTypeEnum;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.service.PmsActionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法操作接口实现
 * @date : 2019-12-16 15:33
 **/
@Service
@Slf4j
public class PmsActionServiceImpl implements PmsActionService {

    @Override
    public DeferredResult start(PmsInstance instance) {
        // 构建actionId与deferResult
        String actionId = "action_"+ RandomStringUtils.randomAlphanumeric(8);
        DeferredResult result = DeferredResultUtil.build(actionId, "运行实例操作时,异步队列请求超时");
        // 1.检查instance状态，数据是否存在、合法
        if (!(checkInstanceLegality() && checkDataLegality())){
            throw new EduProjectException("实例或数据不合法");
        }
        // 2.构建对应的request（开始执行）
        String actionStr = getRequestActionByInstanceType(actionId, instance, InstanceActionType.START);
        log.info("action_detail:"+actionStr);

        // 3.向队列发送json化的request,将键值对存入缓存
        DeferredResultCache.put(actionId, result);

        return result;
    }

    /**
     * 检查实例合法性
     * @return
     */
    public boolean checkInstanceLegality(){
        return true;
    }

    /**
     * 检查数据合法性
     * @return
     */
    public boolean checkDataLegality(){
        return true;
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
            case InstanceTypeEnum._GENERATE_EVALUATE:{
                GenerateRequestAction action = new GenerateRequestAction(actionId, instance.getId(), type);
                return action.parse().toString();
            }
        }
        return "no matched action";
    }
}
