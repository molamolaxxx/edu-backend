package com.njupt.hpc.edu.project.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.api.CommonResult;
import com.njupt.hpc.edu.common.cache.DeferredResultCache;
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
    @RabbitListener(queues = "edu_p2j_mq",containerFactory="rabbitListenerContainerFactory")
    public void handler(byte[] message){
        JSONObject jsonObject = JSON.parseObject(new String(message, StandardCharsets.UTF_8));
        log.info("收到python模块的回复:"+jsonObject);
        String actionId = (String) jsonObject.get("actionId");

        // 如果能在缓存中找到DeferredResult，说明该请求处于等待中，需要执行相应返回逻辑
        if (DeferredResultCache.get(actionId) != null) {
            log.info("存在等待请求,可以执行返回逻辑");
            DeferredResultCache.get(actionId).setResult(CommonResult.success(jsonObject));
            DeferredResultCache.del(actionId);
            return;
        }

        // 实例中找不到，判断返回actionId是否为主动响应类型（完成或者失败），执行相应的逻辑或者通知
    }
}
