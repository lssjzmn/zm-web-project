package com.lssj.zmn.server.app.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpMessageListenerNew implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("message_10000: " + message.toString());
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//重新入队
    }
}
