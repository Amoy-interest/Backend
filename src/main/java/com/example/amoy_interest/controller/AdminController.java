package com.example.amoy_interest.controller;

import com.example.amoy_interest.annotation.UserLoginToken;
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

import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.amoy_interest.constant.SecurityConstants.EXPIRATION_TIME;

@RequestMapping("/admins")
@Api(tags = "管理员模块")
@RestController
public class AdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;

    @UserLoginToken
    @ApiOperation(value = "分页获取被举报的blog")
    @GetMapping(value = "/blogs/reported")
    public Msg<List<BlogDTO>> GetReportedBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        List<BlogDTO> blogDTOS = new ArrayList<>();
        List<BlogCount> blogCounts = blogService.getAllReportedBlogs();
        for (BlogCount blogCount : blogCounts) {
            blogDTOS.add(blogService.getSimpleBlogDetail(blogCount.getBlog_id()));
        }
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_REPORTED_BLOG_SUCCESS_MSG, blogDTOS);
    }

    //一次审核一堆还是一次审核一个blog？效率？
    @UserLoginToken
    @ApiOperation(value = "审核blog")
    @PutMapping(value = "/blogs/reported")
    public Msg CheckReportedBlog(@RequestBody BlogCheckDTO blogCheckDTO) {
        Blog blog = blogService.findBlogByBlog_id(blogCheckDTO.getBlog_id());
        blog.setCheck_status(blogCheckDTO.getCheck_status());
        blogService.updateBlog(blog);
        return new Msg(MsgCode.SUCCESS, MsgUtil.CHECK_BLOG_SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "分页获取被举报的话题")
    @GetMapping(value = "/topics/reported")
    public Msg<List<TopicReportDTO>> GetReportedTopics(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, topicService.getReportedTopics());
    }

    @UserLoginToken
    @ApiOperation(value = "审核话题")
    @PutMapping(value = "/topics/reported")
//    @ResponseBody
    public Msg CheckReportedTopic(@RequestBody TopicCheckDTO topicCheckDTO) {
        topicService.checkReportedTopic(topicCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "分页获取被举报用户", notes = "获取被举报用户")
    @RequestMapping(value = "/users/reported", method = RequestMethod.GET)
    public Msg<List<UserReportDTO>> GetReportedUser(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, userService.getReportedUsers());
    }

    @UserLoginToken
    @ApiOperation(value = "用户禁言", notes = "对用户禁言")
    @RequestMapping(value = "/users/ban", method = RequestMethod.PUT)
    public Msg Ban(@RequestBody UserCheckDTO userCheckDTO) {
        userService.ban(userCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "用户解禁", notes = "对用户解除禁言")
    @RequestMapping(value = "/users/unban", method = RequestMethod.PUT)
    public Msg Unban(@RequestBody Integer user_id) {
        userService.unban(user_id);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "用户封号", notes = "对用户封号")
    @RequestMapping(value = "/users/forbid", method = RequestMethod.PUT)
    public Msg Forbid(@RequestBody UserCheckDTO userCheckDTO) {
        userService.forbid(userCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "用户解封", notes = "对用户解除封号")
    @RequestMapping(value = "/users/permit", method = RequestMethod.PUT)
    public Msg Permit(@RequestBody Integer user_id) {
        userService.permit(user_id);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }


}
