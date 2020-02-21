package com.njupt.hpc.edu.project.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.cache.DeferredResultCache;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 队列监听器
 * @date : 2019-12-17 10:30
 **/
@Component
@Slf4j
public class EduMQListener {

    @Autowired
    private EduMQService mqService;

    @RabbitHandler
    @RabbitListener(queues = QueueEnum.PYTHON_TO_JAVA_QUEUE_NAME, containerFactory="rabbitListenerContainerFactory")
    public void handler(byte[] message){
        JSONObject jsonObject = JSON.parseObject(new String(message, StandardCharsets.UTF_8));
        log.info("收到python模块的回复:"+jsonObject);
        String actionId = (String) jsonObject.get("actionId");
        String instanceId = (String)jsonObject.get("instanceId");
        String actionTypeId = (String) jsonObject.get("actionTypeId");

        // 如果能在缓存中找到DeferredResult，说明该请求处于等待中，需要执行相应返回逻辑
        if (null != actionId && DeferredResultCache.get(actionId) != null) {
            log.info("存在等待请求,可以执行返回逻辑");
            DeferredResultCache.get(actionId).setResult(CommonResult.success(jsonObject));
            DeferredResultCache.del(actionId);
            return;
        }

        // 实例中找不到，判断返回actionTypeId是否为主动响应类型（完成或者失败），执行相应的逻辑或者通知
        /**
         * 主动响应
         * 1.ERROR("105","实例运行失败通知"),
         * 2.FINISH("106","实例运行完毕通知");
         */
        // 实例运行失败
        if (actionTypeId.equals(InstanceActionType._ERROR)){
            mqService.handlerError(instanceId);
        }
        // 实例运行完成
        if (actionTypeId.equals(InstanceActionType._FINISH)){
            mqService.handlerFinish(instanceId);
        }
    }

    /**
     * 死信队列监听
     */
    @RabbitHandler
    @RabbitListener(queues = QueueEnum.PYTHON_TO_JAVA_QUEUE_CANCEL_NAME)
    public void handlerCancel(String message) {
        log.info("死信队列监听:{}", message);
    }
}
