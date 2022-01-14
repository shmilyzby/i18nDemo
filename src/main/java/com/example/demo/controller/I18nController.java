package com.example.demo.controller;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Locale;

@RestController
public class I18nController {

    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();


    @PostConstruct
    public void initialize(){
        messageSource.setBasename("i18n/message");
        messageSource.setDefaultEncoding("UTF-8");
    }

    @GetMapping
    @RequestMapping("/getMessage")
    public String getMessage(){

        String key = "login.settlement_list.mobile.valid_sms_code";
        Object[] args = {"name", "20"};

        String cn = messageSource.getMessage(key, args, Locale.CHINA);
        String us = messageSource.getMessage(key, args, Locale.US);

        System.out.println(cn);
        System.out.println(us);

        return us;
    }
}
