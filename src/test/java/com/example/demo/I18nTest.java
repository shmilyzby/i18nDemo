package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class I18nTest {


    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    @BeforeEach
    public void init(){
        messageSource.setBasename("i18n/message");
        messageSource.setDefaultEncoding("UTF-8");
    }

    @Test
    public void test(){
        String key = "validator.error_message.string_length_must_not_great_than";
        Object[] args = {"name", "20"};

        String cn = messageSource.getMessage(key, args, Locale.CHINA);
        String us = messageSource.getMessage(key, args, Locale.US);

        System.out.println(cn);
        System.out.println(us);


    }
}
