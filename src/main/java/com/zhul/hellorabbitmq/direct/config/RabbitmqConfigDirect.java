package com.zhul.hellorabbitmq.direct.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhul
 * @create 2022/1/10 10:22
 * <p>
 * Fanout Exchange(订阅 模式)
 */
@Configuration
public class RabbitmqConfigDirect {

    /**
     * 两个消息队列名称
     */
    final static String queueA = "queue_direct_a";
    final static String queueB = "queue_direct_b";

    /**
     * 交换机名称
     */
    final static String exchange = "directExchange";


    /**
     * params ： 1、队列名  2、是否持久化 3、是否独占  4、是否自动删除队列
     */
    @Bean
    public Queue queueDirectA() {
        return new Queue(RabbitmqConfigDirect.queueA, false, false, true);
    }

    @Bean
    public Queue queueDirectB() {
        return new Queue(RabbitmqConfigDirect.queueB, false, false, true);
    }

    /**
     * @return
     * @method 声明一个fanout类型的交换机
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RabbitmqConfigDirect.exchange, false, true);
    }

    /**
     * @param queueDirectA   对应注入queueDirectA()方法
     * @param directExchange 对应directExchange()
     * @return
     * @method 绑定Queue队列到交换机, 并且指定routingKey
     */
    @Bean
    Binding bindingDirectExchangeA(Queue queueDirectA, DirectExchange directExchange) {
        return BindingBuilder.bind(queueDirectA).to(directExchange).with("directKeyA");
    }

    @Bean
    Binding bindingDirectExchangeB(Queue queueDirectB, DirectExchange directExchange) {
        return BindingBuilder.bind(queueDirectB).to(directExchange).with("directKeyB");
    }

}
