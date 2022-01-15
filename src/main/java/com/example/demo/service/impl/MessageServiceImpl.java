package com.example.demo.service.impl;

import com.example.demo.mapper.MessageMapper;
import com.example.demo.pojo.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<Message> queryAllMessage() {
        return messageMapper.queryAllMessage();
    }
}
