package com.njupt.hpc.edu.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 队列监听器
 * @date : 2019-12-17 10:30
 **/
@Component
@Slf4j
@RabbitListener(queues = "edu_p2j_mq")
public class EduMQListener {

    // todo 写返回模块
    @RabbitHandler
    public void handler(String message){
        log.info("模拟python模块，接收的message为:"+message);
    }
}
