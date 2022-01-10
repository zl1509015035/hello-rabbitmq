package com.zhul.hellorabbitmq.product;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author zhul
 * @create 2022/1/10 16:41
 */
@Component
public class ProducerDead {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * @throws Exception
     * @method 生产者发送消息, direct模式下需要传递一个routingKey
     * @author Mr yi
     * @time 2019年6月19日
     */
    public void send() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eamil", "123456@qq.com");
        jsonObject.put("message", "Hello World!");
        jsonObject.put("timestamp", 0);
        String jsonString = jsonObject.toJSONString();
        System.out.println("生产者发送的消息是:" + jsonString);
        // 设置消息唯一id 保证每次重试消息id唯一
        Message message = MessageBuilder.withBody(jsonString.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                .setMessageId(UUID.randomUUID() + "").build(); //消息id设置在请求头里面 用UUID做全局ID

        //routingKey 为 keyDemo3
        rabbitTemplate.convertAndSend("emailExchange", "emailKey", message);
    }

}
