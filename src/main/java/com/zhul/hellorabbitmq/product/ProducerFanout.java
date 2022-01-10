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
public class ProducerFanout {

    @Autowired
    private AmqpTemplate amqpTemplate;

    //生产者发送多个消息
    public void send() {
        for (int i = 0; i <= 10; i++) {
            String context = "fanout message";
            System.out.println("Producers sends message: " + context);
            this.amqpTemplate.convertAndSend("fanoutExchange","", context);
        }
    }
}
