package com.zhul.hellorabbitmq.controller;

import com.zhul.hellorabbitmq.product.ProducerDirect;
import com.zhul.hellorabbitmq.product.ProducerFanout;
import com.zhul.hellorabbitmq.product.ProducerSimple;
import com.zhul.hellorabbitmq.product.ProducerWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Thread.sleep(5000);
        producerDirect.sendB();
    }
}
