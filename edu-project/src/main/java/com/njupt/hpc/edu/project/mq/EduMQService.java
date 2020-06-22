package com.njupt.hpc.edu.project.mq;

import com.alibaba.fastjson.JSONObject;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.enumerate.QueueEnum;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-17 10:06
 **/
@Service
@Slf4j
public class EduMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PmsInstanceService instanceService;

    @Autowired
    private PmsResultService resultService;

    public Boolean sendMessageToMQ(String message){
        rabbitTemplate.convertAndSend(QueueEnum.JAVA_TO_PYTHON_QUEUE.getExchange(),
                QueueEnum.JAVA_TO_PYTHON_QUEUE.getRouteKey(),
                message);
        return true;
    }

    /**
     * 处理python模块发来的完成消息
     */
    @Transactional
    public void handlerFinish(String instanceId, JSONObject jsonObject){
        // 1.更改数据库状态为完成
        instanceService.updateInstanceState(instanceId, InstanceActionType._FINISH);
        log.info("instanceId:{} 已完成评价", instanceId);
        // 2.保存到result表中（因为事务的行锁锁住了实例记录，所以此时实例不可能被删除，则不可能出现结果记录悬挂的情况）
        ResultDTO dto = new ResultDTO();
        dto.setId(IdUtil.generateId("temp"));
        dto.setContent(jsonObject.get("finalMonitorResult").toString());
        dto.setInstanceId(instanceId);
        dto.setPath(jsonObject.get("resultPath").toString());
        resultService.create(dto);
    }

    /**
     * 处理python模块发来的错误消息
     */
    public void handlerError(String instanceId, JSONObject jsonObject){
        // 1.更改数据库状态为错误
        instanceService.updateInstanceState(instanceId, InstanceActionType._ERROR);
        log.info("instanceId:{} 评价过程中发生错误",instanceId);
    }

    // 消息确认消费
    public void ackMessage(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new EduProjectException("消息确认失败");
        }
    }

    // 消息拒绝
    public void rejectMessage(Channel channel, Message message) {
        try {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new EduProjectException("消息拒绝失败");
        }
    }
}
