package com.njupt.hpc.edu.config;

import com.njupt.hpc.edu.project.enumerate.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-18 10:05
 * rabbitmq配置
 **/
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * 申明队列交换器
     * @return
     */
    @Bean
    public DirectExchange j2pExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.JAVA_TO_PYTHON_QUEUE.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 申明质量评价的发送队列
     */
    @Bean
    public Queue sender(){
        return QueueBuilder
                .durable(QueueEnum.JAVA_TO_PYTHON_QUEUE.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.JAVA_TO_PYTHON_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.JAVA_TO_PYTHON_CANCEL.getRouteKey())//到期后转发的路由键
                .withArgument("x-message-ttl", 4000L)
                .build();
    }

    /**
     * 申明知识抽取的发送队列
     */
    @Bean
    public Queue senderGenerate(){
        return QueueBuilder
                .durable(QueueEnum.JAVA_TO_PYTHON_QUEUE_GENERATE.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.JAVA_TO_PYTHON_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.JAVA_TO_PYTHON_CANCEL.getRouteKey())//到期后转发的路由键
                .withArgument("x-message-ttl", 20000L)
                .build();
    }

    /**
     * 接收python的消息队列
     * @return
     */
    @Bean
    public Queue receiver(){
        return new Queue(QueueEnum.PYTHON_TO_JAVA_QUEUE.getName());
    }

    /**
     * 申明发送队列的死信队列
     */
    @Bean
    public Queue cancel(){
        return new Queue(QueueEnum.JAVA_TO_PYTHON_CANCEL.getName());
    }

    /**
     * 绑定发送队列到交换器
     */
    @Bean
    public Binding bindingSender(){
        return BindingBuilder
                .bind(sender())
                .to(j2pExchange())
                .with(QueueEnum.JAVA_TO_PYTHON_QUEUE.getRouteKey());
    }

    @Bean
    public Binding bindingSenderGenerate(){
        return BindingBuilder
                .bind(senderGenerate())
                .to(j2pExchange())
                .with(QueueEnum.JAVA_TO_PYTHON_QUEUE_GENERATE.getRouteKey());
    }

    @Bean
    public Binding bindingCancel(){
        return BindingBuilder
                .bind(cancel())
                .to(j2pExchange())
                .with(QueueEnum.JAVA_TO_PYTHON_CANCEL.getRouteKey());
    }
}
