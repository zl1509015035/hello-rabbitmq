package com.zhul.hellorabbitmq.delay.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author zhul
 * @create 2022/1/17 10:32
 */
@Slf4j
@Component
public class DelayConsumer {


    @RabbitListener(queues = "delay_deal_queue")
    public void process(String order, Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {

        log.info("【 监听到延时队列消息】 - 【消费时间】 - [{}]- 【订单内容】 - [{}]", new Date(), order);
        // 判断订单是否已经支付，如果支付则；否则，取消订单（逻辑代码省略)

        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);
        System.out.println("执行结束....");
    }
}
