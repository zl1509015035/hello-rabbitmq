package com.zhul.hellorabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhul
 * @create 2022/1/10 10:22
 * <p>
 * Fanout Exchange(订阅 模式)
 */
@Configuration
public class RabbitmqConfigFanout {

    /**
     * 两个消息队列名称
     */
    final static String queueA = "queue_fanout_a";
    final static String queueB = "queue_fanout_b";

    /**
     *	 交换机名称
     */
    final static String exchange = "fanoutExchange";


    /**
     *  params ： 1、队列名  2、是否持久化 3、是否独占  4、是否自动删除队列
     */
    @Bean
    public Queue queueFanoutA() {
        return new Queue(RabbitmqConfigFanout.queueA,false,false,true);
    }

    @Bean
    public Queue queueFanoutB() {
        return new Queue(RabbitmqConfigFanout.queueB,false,false,true);
    }

    /**
     *
     * @method 声明一个fanout类型的交换机
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitmqConfigFanout.exchange,false,true);
    }

    /**
     *
     * @method 绑定Queue队列到交换机,并且指定routingKey
     * @param queueFanoutA 对应注入queueFanoutA()方法
     * @param fanoutExchange 对应fanoutExchange()
     * @return
     */
    @Bean
    Binding bindingFanoutExchangeA(Queue queueFanoutA, FanoutExchange  fanoutExchange) {
        return BindingBuilder.bind(queueFanoutA).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutExchangeB(Queue queueFanoutB, FanoutExchange  fanoutExchange) {
        return BindingBuilder.bind(queueFanoutB).to(fanoutExchange);
    }

}
