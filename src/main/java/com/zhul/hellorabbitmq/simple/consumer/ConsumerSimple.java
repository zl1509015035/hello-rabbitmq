package com.zhul.hellorabbitmq.simple.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhul
 * @create 2022/1/10 10:30
 */
@Component
@RabbitListener(queues = "queue_simple")
public class ConsumerSimple {

    //@RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，
    //具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型

    @RabbitHandler
    public void process(String message) {
        System.out.println("Consumers receiver message : " + message);
    }

    @RabbitHandler
    public void process(byte[] message) {
        System.out.println("Consumers receiver message : " + new String(message));
    }

}
