package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.TopicHeat;
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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "话题or热榜模块")
@RequestMapping("/topics")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "查看话题基本内容")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<TopicDTO> GetTopicAll(@NotNull(message = "话题名不能为空")
                                     @NotEmpty(message = "话题名不能为空字符串")
                                     @Length(max = 40, message = "话题名不能大于40位")
                                     @RequestParam(required = true) String topic_name) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, topicService.getTopicDTOByName(topic_name));
    }

    @ApiOperation(value = "按照分页形式查看话题的博文")
    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public Msg<CommonPage<BlogDTO>> GetBlogs(@NotNull(message = "话题名不能为空")
                                             @NotEmpty(message = "话题名不能为空字符串")
                                             @Length(max = 40, message = "话题名不能大于40位")
                                             @RequestParam(required = true) String topic_name,
                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                             @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        Integer topic_id = topicService.getTopic_idByName(topic_name);
        System.out.println(topic_name);
        System.out.println(topic_id);
        return new Msg<CommonPage<BlogDTO>>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getListByTopic_id(topic_id, pageNum, pageSize)));
    }


    @ApiOperation(value = "编辑话题简介")
    @PutMapping(value = "/intro")
    public Msg<TopicDTO> ModifyTopicIntro(@RequestBody @Valid TopicIntroDTO topicIntroDTO) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG,topicService.modifyTopicIntro(topicIntroDTO));
    }

    @ApiOperation(value = "新增话题")
    @PostMapping(value = "")
    public Msg AddTopic(@NotNull(message = "话题名不能为空")
                        @NotEmpty(message = "话题名不能为空字符串")
                        @Length(max = 40, message = "话题名不能大于40位") String topic_name) {
        topicService.addTopic(topic_name);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @ApiOperation(value = "编辑话题logo")
    @PutMapping(value = "/logo")
    public Msg<TopicDTO> ModifyTopicLogo(@RequestBody @Valid TopicLogoDTO topicLogoDTO) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG,topicService.modifyTopicLogo(topicLogoDTO));
    }

    @ApiOperation(value = "获取热榜(按照reddit算法简单实现)")
    @RequestMapping(value = "/hotList", method = RequestMethod.GET)
    public Msg<CommonPage<TopicHeatResult>> GetHotList(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(topicService.getHotList(pageNum, pageSize)));
    }

    @ApiOperation(value = "举报话题")
    @PostMapping(value = "/report")
    public Msg ReportTopic(@NotNull(message = "话题名不能为空")
                           @NotEmpty(message = "话题名不能为空字符串")
                           @Length(max = 40, message = "话题名不能大于40位") String topic_name) {
        topicService.reportTopicByName(topic_name);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @ApiOperation(value = "测试")
    @GetMapping(value = "/test")
    public void Test() {
        topicService.updateTopicHeat();
    }
}
