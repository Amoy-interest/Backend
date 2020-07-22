package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.utils.CommonPage;
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

    @Autowired
    private BlogService blogService;
    @ApiOperation(value = "查看话题基本内容（还需要给话题加点内容）")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Msg<TopicDTO> GetTopicAll(String topic_name) {
        return new Msg<>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,topicService.getTopicDTOByName(topic_name));
    }
    @ApiOperation(value = "按照分页形式查看话题")
    @RequestMapping(value = "/blogs",method = RequestMethod.GET)
    public Msg<CommonPage<BlogDTO>> GetBlogs(String topic_name, Integer pageNum, Integer pageSize, Integer orderType) {
        Integer topic_id = topicService.getTopic_idByName(topic_name);
        return new Msg<CommonPage<BlogDTO>>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,CommonPage.restPage(blogService.getListByTopic_id(topic_id,pageNum,pageSize)));
    }


    @ApiOperation(value = "获取热榜")
    @RequestMapping(value = "/hotList",method = RequestMethod.GET)
    public Msg<List<String>> GetHotList(Integer count) {
        List<String> list = new ArrayList<>();
        list.add("高考加油");
        list.add("隐秘的角落大结局");
        return new Msg<>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,list);
    }
    @ApiOperation(value = "举报话题")
    @PostMapping(value = "/report")
    public Msg ReportTopic(String topic_name) {
        topicService.reportTopicByName(topic_name);
        return new Msg(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG);
    }

}
