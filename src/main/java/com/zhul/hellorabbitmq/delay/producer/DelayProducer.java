package com.zhul.hellorabbitmq.delay.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhul
 * @create 2022/1/17 10:32
 */
@Slf4j
@Component
public class DelayProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;



    /**
     * @method 生产者发送消息,direct模式下需要传递一个routingKey
     * @author Mr yi
     * @time 2019年6月19日
     * @throws Exception
     */
    public void send(Integer time) {

        log.info("【订单生成时间】" + new Date().toString() +"【1分钟后检查订单是否已经支付】"  );

        this.rabbitTemplate.convertAndSend("delayExchange", "delayKey", "实体类", message -> {
            System.out.println(time*1000);
            message.getMessageProperties().setExpiration(String.valueOf(time * 1000));
            return message;
        });

    }
}
