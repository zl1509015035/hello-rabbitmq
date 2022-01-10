package com.zhul.hellorabbitmq.product;

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
public class ProducerDirect {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * @throws Exception
     * @method 生产者发送消息, direct模式下需要传递一个routingKey
     */
    public void sendA() {
        String context = "direct message";
        System.out.println("Producers sends messageA: " + context);
        this.amqpTemplate.convertAndSend("directExchange", "directKeyA", context);
    }

    /**
     * @throws Exception
     * @method 生产者发送消息, direct模式下需要传递一个routingKey
     */
    public void sendB() {
        String context = "direct message";
        System.out.println("Producers sends messageB: " + context);
        this.amqpTemplate.convertAndSend("directExchange", "directKeyB", context);
    }
}
