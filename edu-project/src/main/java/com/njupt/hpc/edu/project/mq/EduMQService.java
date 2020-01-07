package com.njupt.hpc.edu.project.mq;

import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-17 10:06
 **/
@Service
public class EduMQService {

    private static String QUEUE_NAME = "edu_j2p_mq";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PmsInstanceService instanceService;

    public Boolean sendMessageToMQ(String message){
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        return true;
    }

    /**
     * 处理python模块发来的完成消息
     */
    public void handlerFinish(String instanceId){
        // 1.更改数据库状态为完成
        instanceService.updateInstanceState(instanceId, InstanceActionType._FINISH);
        // todo 2.向socket客户端发送消息
    }

    /**
     * todo 处理python模块发来的错误消息
     */
    public void handlerError(String instanceId){
        // 1.更改数据库状态为错误
        instanceService.updateInstanceState(instanceId, InstanceActionType._ERROR);
        // todo 2.向socket客户端发送消息
    }
}
