package com.njupt.hpc.edu.project.mq;

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

    public Boolean sendMessageToMQ(String message){
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        return true;
    }

    /**
     * todo 处理python模块发来的完成消息
     */
    public void handlerFinish(){
    }

    /**
     * todo 处理python模块发来的错误消息
     */
    public void handlerRunning(){
    }
}
