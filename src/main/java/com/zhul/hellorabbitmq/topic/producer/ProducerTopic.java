package com.zhul.hellorabbitmq.topic.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhul
 * @create 2022/1/10 10:27
 * <p>
 * 生产者
 */
@Component
public class ProducerTopic {


    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     *
     * @method 生产者消息,并指定routingKey为topic.keyA
     * @author Mr yi
     * @time 2019年6月19日
     * @throws Exception
     */
    public void sendA() throws Exception {
        String context = "topic messageA";
        System.out.println("Producers sends messageA: " + context);
        this.amqpTemplate.convertAndSend("topicExchange", "topic.keyA", context);
    }

    /**
     *
     * @method 生产者消息,并指定routingKey为topic.keyB
     * @author Mr yi
     * @time 2019年6月19日
     * @throws Exception
     */
    public void sendB() throws Exception {
        String context = "topic messageB";
        System.out.println("Producers sends messageB: " + context);
        this.amqpTemplate.convertAndSend("topicExchange", "topic.keyB", context);
    }

}
