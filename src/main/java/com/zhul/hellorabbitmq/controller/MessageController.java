package com.zhul.hellorabbitmq.controller;

import com.zhul.hellorabbitmq.dead.producer.ProducerDead;
import com.zhul.hellorabbitmq.delay.producer.DelayProducer;
import com.zhul.hellorabbitmq.direct.producer.ProducerDirect;
import com.zhul.hellorabbitmq.fanout.producer.ProducerFanout;
import com.zhul.hellorabbitmq.simple.producer.ProducerSimple;
import com.zhul.hellorabbitmq.topic.producer.ProducerTopic;
import com.zhul.hellorabbitmq.work.producer.ProducerWork;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhul
 * @create 2022/1/10 10:33
 */
@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private ProducerSimple producerSimple;

    @Autowired
    private ProducerWork producerWork;

    @Autowired
    private ProducerFanout producerFanout;

    @Autowired
    private ProducerDirect producerDirect;

    @Autowired
    private ProducerTopic producerTopic;

    @Autowired
    private ProducerDead producerDead;

    @Autowired
    private DelayProducer producerDelay;

    @PostMapping("/send")
    public void send(){
        producerSimple.send();
    }

    @PostMapping("/sendWork")
    public void sendWork() throws Exception {
        producerWork.send();
    }

    @PostMapping("/sendFanout")
    public void sendFanout() throws Exception {
        producerFanout.send();
    }

    @PostMapping("/sendDirect")
    public void sendDirect() throws Exception {
        producerDirect.sendA();
        Thread.sleep(3000);
        producerDirect.sendB();
    }

    @PostMapping("/sendTopic")
    public void sendTopic() throws Exception {
        producerTopic.sendA();
        producerTopic.sendB();
    }

    @RequestMapping("/sendEmail")
    public String sendEmail() throws Exception {
        producerDead.send();
        return "success";
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendDelayMsg")
    public String sendDelayMsg(@RequestParam(value = "time") Integer time) throws Exception {
/*        rabbitTemplate.execute(channel -> {
            channel.queueDeclare();

            channel.exchangeDeclare()
        })*/
        producerDelay.send(time);
        return "success";
    }
}
