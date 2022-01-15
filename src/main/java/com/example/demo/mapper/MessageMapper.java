package com.example.demo.mapper;

import com.example.demo.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> queryAllMessage();

}
