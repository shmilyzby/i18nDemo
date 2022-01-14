package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/test")
    public String test(User user, Student student){
        JSONArray array = new JSONArray();
        array.add(user);
        array.add(student);
        return array.toJSONString();
    }


    @GetMapping("/testGet")
    public String testGet(User user, Student student){
        JSONArray array = new JSONArray();
        array.add(user);
        array.add(student);
        return array.toJSONString();
    }

}
