package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Locale;

@RestController
public class I18nController {

//    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();


    @Autowired
    CustomMessageSource messageSource;

//    @PostConstruct
//    public void initialize(){
//        messageSource.setBasename("i18n/message");
//        messageSource.setDefaultEncoding("UTF-8");
//    }

    @GetMapping
    @RequestMapping("/getMessage")
    public String getMessage(){

//        String key = "account.settlementList.receive_cash";
//        Object[] args = {"10"};

//        String cn = messageSource.getMessage(key, args, Locale.CHINA);
//        String us = messageSource.getMessage(key, args, Locale.US);
//
//        System.out.println(cn);
//        System.out.println(us);


        Object[] args = {"18"};
        String cn = messageSource.getSourceFromCache("account.settlementList.receive_cash", args, Locale.CHINA);
        String us = messageSource.getSourceFromCache("account.settlementList.receive_cash", args, Locale.ENGLISH);

        System.out.println(cn);
        System.out.println(us);

        JSONArray array = new JSONArray();
        array.add(cn);
        array.add(us);
        return array.toJSONString();
    }
}
