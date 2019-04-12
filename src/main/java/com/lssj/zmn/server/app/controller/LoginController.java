package com.lssj.zmn.server.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/entry")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/welcome.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        logger.info("login info:" + request.getContentType());
        return "/login";
    }

}
