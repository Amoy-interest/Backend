package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags="话题/热榜模块")
@RequestMapping("/topics")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;
    @ApiOperation(value = "查看话题")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Msg<TopicDTO> GetTopic(String topic_name) {
        return new Msg<>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,topicService.getTopicByName(topic_name));
    }

    @ApiOperation(value = "获取热榜")
    @RequestMapping(value = "/hotList",method = RequestMethod.GET)
    public Msg<List<String>> GetHotList() {
        List<String> list = new ArrayList<>();
        list.add("高考加油");
        list.add("隐秘的角落大结局");
        return new Msg<>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,list);
    }

}
