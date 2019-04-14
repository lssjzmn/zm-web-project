package com.lssj.zmn.server.app.utils.converters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;

public class AmqpMessageConverter {

    public static Object convertMsgToObject(Message message, Class clazz) {
        byte[] messageBytes = message.getBody();
        String messageJsonStr = new String(messageBytes);
        JSONObject jsonObject = JSON.parseObject(messageJsonStr);
        Object retObject = JSONObject.toJavaObject(jsonObject, clazz);
        return retObject;
    }

}
