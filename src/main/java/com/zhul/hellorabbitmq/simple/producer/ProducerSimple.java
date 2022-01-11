package com.zhul.hellorabbitmq.simple.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhul
 * @create 2022/1/10 10:27
 *
 * 生产者
 */
@Component
public class ProducerSimple {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String context = "Hello world!";

        System.out.println("Producers sends message: " + context);
        //简单队列的情况下routingKey即为队列名queue_simple
        this.amqpTemplate.convertAndSend("queue_simple", context);
    }
}
