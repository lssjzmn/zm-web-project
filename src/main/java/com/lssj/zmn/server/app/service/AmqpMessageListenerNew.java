package com.lssj.zmn.server.app.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpMessageListenerNew implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("message_10000: " + message.toString());
    }
}
