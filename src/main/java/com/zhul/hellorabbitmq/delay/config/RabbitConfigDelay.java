package com.zhul.hellorabbitmq.delay.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhul
 * @create 2022/1/17 10:24
 */
@Slf4j
@Configuration
public class RabbitConfigDelay {

    //队列名称
    final static String delay_queue = "delay_queue";

    //交换机名称
    final static String delay_exchange_name = "delayExchange";

    // routingKey
    final static String delay_routingKey  = "delayKey";

    //死信消息队列名称
    final static String deal_queue = "delay_deal_queue";

    //死信交换机名称
    final static String deal_exchangeName = "delayDealExchange";

    //死信 routingKey
    final static String dead_RoutingKey  = "delayDealKey";

    //死信队列 交换机标识符
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    //死信队列交换机绑定键标识符
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    /**
     *
     * @method 定义队列(队列 绑定一个死信交换机,并指定routing_key)
     * @author Mr yi
     * @time 2019年6月29日
     * @return
     */
    @Bean
    public Queue delayQueue() {
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        //args.put("x-message-ttl", 5 * 1000);//直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活
        //这里采用发送消息动态设置延迟时间,这样我们可以灵活控制
        args.put(DEAD_LETTER_QUEUE_KEY, deal_exchangeName);
        args.put(DEAD_LETTER_ROUTING_KEY, dead_RoutingKey);
        return new Queue(RabbitConfigDelay.delay_queue, true, false, false, args);
    }

    //声明一个direct类型的交换机
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(RabbitConfigDelay.delay_exchange_name,false,false);
    }

    //绑定Queue队列到交换机,并且指定routingKey
    @Bean
    Binding bindingDirectExchangeDemo(   ) {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delay_routingKey);
    }

    //创建配置死信队列
    @Bean
    public Queue delayDeadQueue() {
        Queue queue = new Queue(deal_queue, true,false,false);
        return queue;
    }

    //创建死信交换机
    @Bean
    public DirectExchange delayDeadExchange() {
        return new DirectExchange(deal_exchangeName);
    }

    //死信队列与死信交换机绑定
    @Bean
    public Binding delayBindingDeadExchange() {
        return BindingBuilder.bind(delayDeadQueue()).to(delayDeadExchange()).with(dead_RoutingKey);
    }




}
