package com.zhul.hellorabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhul
 * @create 2022/1/10 10:22
 * <p>
 * 简单队列
 */
@Configuration
public class RabbitmqConfigWork {

    /**
     *
     */
    @Bean
    public Queue queueWork() {
        return new Queue("queue_work",false,false,true);
    }
}
