package com.zhul.hellorabbitmq.work.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhul
 * @create 2022/1/10 10:22
 * <p>
 * įŽåéå
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
