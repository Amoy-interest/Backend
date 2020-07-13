package com.example.demo.controller;

import com.example.demo.dto.BlogDTO;
import com.example.demo.dto.TopicDTO;
import com.example.demo.msgutils.Msg;
import com.example.demo.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="话题/热榜模块")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;
    @ApiOperation(value = "查看话题")
    @RequestMapping(value = "/topic",method = RequestMethod.GET)
    public TopicDTO GetTopic(String topic_name) {
        return null;
    }

    @ApiOperation(value = "获取热榜")
    @RequestMapping(value = "/hotList",method = RequestMethod.GET)
    public List<String> GetHotList() {
        return null;
    }
}
