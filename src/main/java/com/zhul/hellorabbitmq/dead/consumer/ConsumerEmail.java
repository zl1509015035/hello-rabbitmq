package com.zhul.hellorabbitmq.dead.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhul
 * @create 2022/1/10 16:42
 */
@Component
public class ConsumerEmail {

    @RabbitListener(queues = "email_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {

        // 获取消息Id
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("邮件消费者获取生产者消息msg:" + msg + ",消息id" + messageId);

        JSONObject jsonObject = JSONObject.parseObject(msg);
        Integer timestamp = jsonObject.getInteger("timestamp");

        try {
            int result = 1 / timestamp;
            System.out.println("result" + result);
            //手动ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            // 手动签收
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //拒绝消费消息（丢失消息） 给死信队列,第三个参数 false 表示不会重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }

    }
}
