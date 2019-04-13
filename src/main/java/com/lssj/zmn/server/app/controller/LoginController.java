package com.lssj.zmn.server.app.controller;

import com.lssj.zmn.server.app.service.AmqpMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/entry")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AmqpMessageSender amqpMessageSender;

    @RequestMapping(value = "/welcome.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        logger.info("login info:" + request.getContentType());
        amqpMessageSender.sendMessage("rabbitmq_queue_routingkey",
                "this is a rabbitmq message for rabbitmq_queue_routingkey.");
        amqpMessageSender.sendMessage("rabbitmq_queue_routingkey_10000",
                "this is a rabbitmq message for rabbitmq_queue_routingkey_1000.");
        System.out.println("login info:" + request.getRequestURI());
        return "/login";
    }

}
