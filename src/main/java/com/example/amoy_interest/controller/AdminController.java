package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admins")
@Api(tags="管理员模块")
@RestController
public class AdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;
    @ApiOperation(value = "获取被举报的blog")
    @GetMapping(value = "/blogs/reported")
    public Msg<List<BlogDTO>> GetReportedBlogs() {
        List<BlogDTO> blogDTOS = new ArrayList<>();
        List<BlogCount> blogCounts = blogService.getAllReportedBlogs();
        for(BlogCount blogCount : blogCounts) {
            blogDTOS.add(blogService.getSimpleBlogDetail(blogCount.getBlog_id()));
        }
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_REPORTED_BLOG_SUCCESS_MSG, blogDTOS);
    }

    //一次审核一堆还是一次审核一个blog？效率？
    @ApiOperation(value = "审核blog")
    @PutMapping(value = "/blogs/reported")
    public Msg CheckReportedBlog(@RequestBody BlogCheckDTO blogCheckDTO) {
        Blog blog = blogService.findBlogByBlog_id(blogCheckDTO.getBlog_id());
        blog.setCheck_status(blogCheckDTO.getCheck_status());
        blogService.updateBlog(blog);
        return new Msg(MsgCode.SUCCESS, MsgUtil.CHECK_BLOG_SUCCESS_MSG);
    }
    @ApiOperation(value = "获取被举报的话题")
    @GetMapping(value = "/topics/reported")
    public Msg<List<TopicReportDTO>> GetReportedTopics() {
        return new Msg<>(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG,topicService.getReportedTopics());
    }
    @ApiOperation(value = "审核话题")
    @PutMapping(value = "/topics/reported")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "topic_name", value = "话题名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "check_status", value = "审核状态（1为审核通过，2为审核关闭）", required = true, dataType = "int")
    })
    public Msg CheckReportedBlog(@RequestBody TopicCheckDTO topicCheckDTO) {
        topicService.CheckReportedTopic(topicCheckDTO);
        return new Msg(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG);
    }

    @ApiOperation(value = "获取被举报用户",notes = "获取被举报用户")
    @RequestMapping(value = "/users/reported", method = RequestMethod.GET)
    public Msg<List<UserReportDTO>> GetReportedUser() {

        return null;
    }

    @ApiOperation(value = "用户禁言",notes = "对用户禁言")
    @RequestMapping(value = "/users/ban", method = RequestMethod.PUT)
    public Msg Ban(@RequestBody UserCheckDTO userCheckDTO) {

        return null;
    }

    @ApiOperation(value = "用户解禁",notes = "对用户解除禁言")
    @RequestMapping(value = "/users/unban", method = RequestMethod.PUT)
    public Msg Unban(@RequestBody Integer user_id) {
        return null;
    }

    @ApiOperation(value = "用户封号",notes = "对用户封号")
    @RequestMapping(value = "/users/forbid", method = RequestMethod.PUT)
    public Msg Forbid(@RequestBody UserCheckDTO userCheckDTO) {
        return null;
    }

    @ApiOperation(value = "用户解封",notes = "对用户解除封号")
    @RequestMapping(value = "/users/permit", method = RequestMethod.PUT)
    public Msg Permit(@RequestBody Integer user_id) {
        return null;
    }
}
