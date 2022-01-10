package com.zhul.hellorabbitmq.controller;

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

    @PostMapping("/send")
    public void send(){
        producerSimple.send();
    }

    @PostMapping("/sendWork")
    public void sendWork() throws Exception {
        producerWork.send();
    }
}
