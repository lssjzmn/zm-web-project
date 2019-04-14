package com.lssj.zmn.server.app.service;

import com.lssj.zmn.server.app.bo.LoginRet;
import com.lssj.zmn.server.app.utils.converters.AmqpMessageConverter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpMessageListener extends AsynMsgWorker implements MessageListener {
    @Override
    protected String getName() {
        return "amqpMessageListener";
    }

    @Override
    protected void processMsg(Object object) {
        if (object instanceof Message) {
            Message message = (Message) object;
            LoginRet loginRet = (LoginRet) AmqpMessageConverter.convertMsgToObject(message, LoginRet.class);
            System.out.println("loginRet message: " + loginRet.getInfo());
        }
    }

    @Override
    public void onMessage(Message message) {
        onMsgReceived(message);
    }
}
