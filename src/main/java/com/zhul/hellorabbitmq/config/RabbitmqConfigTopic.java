package com.zhul.hellorabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhul
 * @create 2022/1/10 10:22
 * <p>
 * Topic 主体模式
 */
@Configuration
public class RabbitmqConfigTopic {

    /**
     * 两个消息队列名称
     */
    final static String queueA = "queue_topic_a";
    final static String queueB = "queue_topic_b";

    /**
     *	 交换机名称
     */
    final static String exchange = "topicExchange";


    @Bean
    public Queue queueTopicA() {
        return new Queue(RabbitmqConfigTopic.queueA);
    }

    @Bean
    public Queue queueTopicB() {
        return new Queue(RabbitmqConfigTopic.queueB);
    }

    /**
     *
     * @method 声明一个Topic类型的交换机
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(RabbitmqConfigTopic.exchange);
    }

    /**
     *
     * @method 绑定Queue队列到交换机,并且指定routingKey
     * @return
     */
    @Bean
    Binding bindingTopicExchangeA(Queue queueTopicA, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueTopicA).to(topicExchange).with("topic.keyA");
    }

    @Bean
    Binding bindingTopicExchangeB(Queue queueTopicB, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueTopicB).to(topicExchange).with("topic.#");
    }

}
