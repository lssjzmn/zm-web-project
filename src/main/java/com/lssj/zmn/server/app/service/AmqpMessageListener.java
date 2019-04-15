package com.lssj.zmn.server.app.service;

import com.lssj.zmn.server.app.bo.LoginRet;
import com.lssj.zmn.server.app.utils.converters.AmqpMessageConverter;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpMessageListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        LoginRet loginRet = (LoginRet) AmqpMessageConverter.convertMsgToObject(message, LoginRet.class);
        System.out.println("loginRet message: " + loginRet.getInfo());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
