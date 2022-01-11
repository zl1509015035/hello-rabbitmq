package com.zhul.hellorabbitmq.work.producer;

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
public class ProducerWork {

    @Autowired
    private AmqpTemplate amqpTemplate;

    //生产者发送多个消息
    public void send() throws Exception {
        for (int i = 0; i <= 10; i++) {
            String context = "Work[" + i + "]";
            System.out.println("Producers sends message: " + context);
            this.amqpTemplate.convertAndSend("queue_work", context);
            Thread.sleep(100);
        }
    }
}
