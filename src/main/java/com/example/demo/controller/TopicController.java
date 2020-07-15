package com.example.demo.controller;

import com.example.demo.dto.BlogDTO;
import com.example.demo.dto.TopicCheckDTO;
import com.example.demo.dto.TopicDTO;
import com.example.demo.dto.TopicReportDTO;
import com.example.demo.entity.Topic;
import com.example.demo.msgutils.Msg;
import com.example.demo.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="话题/热榜模块")
@RequestMapping("/topics")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;
    @ApiOperation(value = "查看话题")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public TopicDTO GetTopic(String topic_name) {
        return null;
    }

    @ApiOperation(value = "获取热榜")
    @RequestMapping(value = "/hotList",method = RequestMethod.GET)
    public List<String> GetHotList() {
        return null;
    }

    @ApiOperation(value = "获取被举报的话题")
    @GetMapping(value = "/reported")
    public List<TopicReportDTO> GetReportedTopics() {
        return null;
    }
    @ApiOperation(value = "审核话题")
    @PutMapping(value = "/reported")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "topic_name", value = "话题名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "check_status", value = "审核状态（1为审核通过，2为审核关闭）", required = true, dataType = "int")
    })
    public Msg CheckReportedBlog(@RequestBody TopicCheckDTO topicCheckDTO) {

        return null;
    }
}
