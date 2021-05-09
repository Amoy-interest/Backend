package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.utils.CommonPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Api(tags = "话题or热榜模块")
@RequestMapping("/topics")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private BlogService blogService;

    @RequiresAuthentication
    @ApiOperation(value = "查看话题基本内容")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<TopicDTO> GetTopicAll(@NotNull(message = "话题名不能为空")
                                     @NotEmpty(message = "话题名不能为空字符串")
                                     @Length(max = 40, message = "话题名不能大于40位")
                                     @RequestParam(required = true) String topic_name) {
        TopicDTO topicDTO = topicService.getTopicDTOByName(topic_name);
        if (topicDTO == null) {
            return new Msg<>(MsgCode.ERROR, "话题不存在", null);
        }
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, topicDTO);
    }

    @RequiresAuthentication
    @ApiOperation(value = "按照分页形式查看话题的博文")
    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public Msg<CommonPage<BlogDTO>> GetBlogs(@NotNull(message = "话题名不能为空")
                                             @NotEmpty(message = "话题名不能为空字符串")
                                             @Length(max = 40, message = "话题名不能大于40位")
                                             @RequestParam(required = true) String topic_name,
                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                             @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        Topic topic = topicService.getTopicByName(topic_name);
        if (topic == null || topic.getCheck_status() == 2) {
            return new Msg<>(MsgCode.ERROR, "该话题不存在或被删除", null);
        }
        if (orderType == 0) {
            return new Msg<CommonPage<BlogDTO>>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getListByTopic_id(topic.getTopic_id(), pageNum, pageSize)));
        }
        return new Msg<CommonPage<BlogDTO>>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getHotBlogPageByTopic_id(topic.getTopic_id(), pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "按照分页形式查看大类博文")
    @RequestMapping(value = "/blogs/group", method = RequestMethod.GET)
    public Msg<CommonPage<BlogDTO>> GetGroupBlogs(@NotNull(message = "大类名不能为空")
                                             @NotEmpty(message = "大类名不能为空字符串")
                                             @Length(max = 40, message = "大类名不能大于40位")
                                             @RequestParam(required = true) String group_name,
                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<CommonPage<BlogDTO>>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getBlogPageByGroupName(group_name, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "编辑话题")
    @PutMapping(value = "")
    public Msg<TopicDTO> ModifyTopicIntro(@RequestBody @Valid TopicModifyParam topicModifyParam) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, topicService.modifyTopic(topicModifyParam));
    }

    @RequiresAuthentication
    @ApiOperation(value = "新增话题")
    @PostMapping(value = "")
    public Msg AddTopic(@RequestParam(required = true)
                        @NotNull(message = "话题名不能为空")
                        @NotEmpty(message = "话题名不能为空字符串")
                        @Length(max = 40, message = "话题名不能大于40位") String topic_name) {
        topicService.addTopic(topic_name);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @ApiOperation(value = "获取热榜")
    @RequestMapping(value = "/hotList", method = RequestMethod.GET)
    public Msg<CommonPage<TopicHeatResult>> GetHotList(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(topicService.getHotList(pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "举报话题")
    @PostMapping(value = "/report")
    public Msg ReportTopic(@RequestParam(required = true)
                           @NotNull(message = "话题名不能为空")
                           @NotEmpty(message = "话题名不能为空字符串")
                           @Length(max = 40, message = "话题名不能大于40位") String topic_name) {
        topicService.reportTopicByName(topic_name);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

//    @ApiOperation(value = "test")
//    @GetMapping(value = "/test")
//    public void Test() throws IOException {
//        topicService.updateAllTopicHeat();
//    }

}
