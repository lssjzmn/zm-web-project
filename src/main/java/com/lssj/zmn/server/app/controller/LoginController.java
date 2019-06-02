package com.lssj.zmn.server.app.controller;

import com.lssj.zmn.server.app.bo.LoginRet;
import com.lssj.zmn.server.app.service.AmqpMessageSender;
import com.lssj.zmn.server.app.utils.util.JSONModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/entry")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AmqpMessageSender amqpMessageSender;

    @RequestMapping(value = "/welcome/{id}.html", method = RequestMethod.GET)
    public String login(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, Model model) {
        LoginRet rabbitLogin = new LoginRet();
        rabbitLogin.setId(10086L);
        rabbitLogin.setStatus("200 OK");
        rabbitLogin.setInfo("this is a rabbitmq message of rabbitLogin");
        rabbitLogin.getBody().put("bofyInfo", 8888);
        amqpMessageSender.sendMessage("rabbitmq_queue_routingkey", rabbitLogin);
        amqpMessageSender.sendMessage("rabbitmq_queue_routingkey",
                "this is a rabbitmq message for rabbitmq_queue_routingkey_10000.id = " + id);
        model.addAttribute("id", id);
        return "/login";
    }

    @RequestMapping(value = "/requestLogin.html", method = RequestMethod.GET)
    @ResponseBody
    public Object requestLogin() {
        LoginRet loginRet = new LoginRet();
        loginRet.setId(10086L);
        loginRet.setStatus("200 OK");
        loginRet.setInfo("login success.");
        loginRet.getBody().put("bofyInfo", 8888);
        String ret = JSONModel.convertObjectToJSON(loginRet);
        return ret;
    }

}
