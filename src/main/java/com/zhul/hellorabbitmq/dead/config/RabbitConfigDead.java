package com.zhul.hellorabbitmq.dead.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhul
 * @create 2022/1/10 16:34
 */
@Slf4j
@Configuration
public class RabbitConfigDead {

    //邮件队列名称
    final static String queue = "email_queue";

    //邮件交换机名称
    final static String exchangeName = "emailExchange";

    // routingKey
    final static String routingKey = "emailKey";

    //死信消息队列名称
    final static String deal_queue = "deal_queue";

    //死信交换机名称
    final static String deal_exchangeName = "dealExchange";

    //死信 routingKey
    final static String dead_RoutingKey = "dealKey";

    //死信队列 交换机标识符
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    //死信队列交换机绑定键标识符
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    @Autowired
    private CachingConnectionFactory connectionFactory;

    //定义邮件队列(邮件队列 绑定一个死信交换机,并指定routing_key)
    @Bean
    public Queue emailQueue() {
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        args.put(DEAD_LETTER_QUEUE_KEY, deal_exchangeName);
        args.put(DEAD_LETTER_ROUTING_KEY, dead_RoutingKey);
        return new Queue(RabbitConfigDead.queue, true, false, false, args);
    }

    //声明一个direct类型的交换机
    @Bean
    DirectExchange emailExchange() {
        return new DirectExchange(RabbitConfigDead.exchangeName,false,true);
    }

    //绑定邮件Queue队列到交换机,并且指定routingKey
    @Bean
    Binding bindingDirectExchangeEmail() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(routingKey);
    }

    //创建配置死信邮件队列
    @Bean
    public Queue deadQueue() {
        Queue queue = new Queue(deal_queue, false,false,true);
        return queue;
    }

    //创建死信交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(deal_exchangeName,false,true);
    }

    //死信队列与死信交换机绑定
    @Bean
    public Binding bindingDeadExchange() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(dead_RoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        //若使用confirm-callback ，必须要配置publisherConfirms 为true
        connectionFactory.setPublisherConfirms(true);
        //若使用return-callback，必须要配置publisherReturns为true
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
        // rabbitTemplate.setMandatory(true);

        // 如果消息没有到exchange,则confirm回调,ack=false; 如果消息到达exchange,则confirm回调,ack=true
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
                } else {
                    log.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
                }
            }
        });

        //如果exchange到queue成功,则不回调return;如果exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
            }
        });
        return rabbitTemplate;
    }
}
