package com.lssj.zmn.server.app.service.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

    }
}
