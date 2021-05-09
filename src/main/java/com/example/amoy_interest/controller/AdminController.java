package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.CountService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.CommonPage;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    CountService countService;

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页获取被举报的blog")
    @GetMapping(value = "/blogs/reported")
    public Msg<CommonPage<BlogDTO>> GetReportedBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                     @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                     @RequestParam(required = false, defaultValue = "1") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_REPORTED_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getReportedBlogsPage(pageNum, pageSize, orderType)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页搜索被举报的blog")
    @GetMapping(value = "/blogs/reported/search")
    public Msg<CommonPage<BlogDTO>> SearchReportedBlogs(@RequestParam(required = true)
                                                        @NotNull(message = "关键词不能为空")
                                                        @NotEmpty(message = "关键词不能为空字符串")
                                                        @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                                        @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                        @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                        @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_REPORTED_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.searchReportedBlogsPage(keyword, pageNum, pageSize, orderType)));
    }

    //一次审核一堆还是一次审核一个blog？效率？
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "审核blog")
    @PutMapping(value = "/blogs/reported")
    public Msg CheckReportedBlog(@RequestBody @Valid BlogCheckDTO blogCheckDTO) {
//        blogService.checkReportedBlog(blogCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.CHECK_BLOG_SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页获取被举报的话题")
    @GetMapping(value = "/topics/reported")
    public Msg<CommonPage<TopicReportDTO>> GetReportedTopics(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                             @RequestParam(required = false, defaultValue = "1") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(topicService.getReportedTopicsPage(pageNum, pageSize, orderType)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页搜索被举报的话题")
    @GetMapping(value = "/topics/reported/search")
    public Msg<CommonPage<TopicReportDTO>> SearchReportedTopics(@RequestParam(required = true)
                                                                @NotNull(message = "关键词不能为空")
                                                                @NotEmpty(message = "关键词不能为空字符串")
                                                                @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                                                @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                                @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                                @RequestParam(required = false, defaultValue = "1") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(topicService.searchReportedTopicsPage(keyword, pageNum, pageSize, orderType)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "审核话题")
    @PutMapping(value = "/topics/reported")
    public Msg CheckReportedTopic(@RequestBody @Valid TopicCheckDTO topicCheckDTO) {
        topicService.checkReportedTopic(topicCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页获取被举报用户", notes = "获取被举报用户")
    @RequestMapping(value = "/users/reported", method = RequestMethod.GET)
    public Msg<CommonPage<UserReportDTO>> GetReportedUser(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                          @RequestParam(required = false, defaultValue = "1") Integer orderType) {
//        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, userService.getReportedUsers());
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getReportedUsersPage(pageNum, pageSize, orderType)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页搜索被举报用户", notes = "搜索被举报用户")
    @RequestMapping(value = "/users/reported/search", method = RequestMethod.GET)
    public Msg<CommonPage<UserReportDTO>> SearchReportedUser(@RequestParam(required = true)
                                                             @NotNull(message = "关键词不能为空")
                                                             @NotEmpty(message = "关键词不能为空字符串")
                                                             @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                             @RequestParam(required = false, defaultValue = "1") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.searchReportedUsersPage(keyword, pageNum, pageSize, orderType)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "用户禁言", notes = "对用户禁言")
    @RequestMapping(value = "/users/ban", method = RequestMethod.PUT)
    public Msg Ban(@RequestBody @Valid UserCheckDTO userCheckDTO) {
        userService.ban(userCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "用户解禁", notes = "对用户解除禁言")
    @RequestMapping(value = "/users/unban", method = RequestMethod.PUT)
    public Msg Unban(@RequestParam(required = true)
                     @NotNull(message = "用户id不能为空")
                     @Min(value = 1, message = "用户id不能小于1") Integer user_id) {
        userService.unban(user_id);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "用户封号", notes = "对用户封号")
    @RequestMapping(value = "/users/forbid", method = RequestMethod.PUT)
    public Msg Forbid(@RequestBody @Valid UserCheckDTO userCheckDTO) {
        userService.forbid(userCheckDTO);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "用户解封", notes = "对用户解除封号")
    @RequestMapping(value = "/users/permit", method = RequestMethod.PUT)
    public Msg Permit(@RequestParam(required = true)
                      @NotNull(message = "用户id不能为空")
                      @Min(value = 1, message = "用户id不能小于1") Integer user_id) {
        userService.permit(user_id);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页获取被禁言用户", notes = "获取被禁言用户")
    @RequestMapping(value = "/users/ban", method = RequestMethod.GET)
    public Msg<CommonPage<UserBanResult>> GetBan(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserBanPage(pageNum, pageSize)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页获取被封号用户", notes = "获取被封号用户")
    @RequestMapping(value = "/users/forbid", method = RequestMethod.GET)
    public Msg<CommonPage<UserForbiddenResult>> GetForbid(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserForbidPage(pageNum, pageSize)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页搜索被禁言用户", notes = "搜索被禁言用户")
    @RequestMapping(value = "/users/ban/search", method = RequestMethod.GET)
    public Msg<CommonPage<UserBanResult>> SearchBan(@RequestParam(required = true)
                                                    @NotNull(message = "关键词不能为空")
                                                    @NotEmpty(message = "关键词不能为空字符串")
                                                    @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.searchUserBanPage(keyword, pageNum, pageSize)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "分页搜索被封号用户", notes = "搜索被封号用户")
    @RequestMapping(value = "/users/forbid/search", method = RequestMethod.GET)
    public Msg<CommonPage<UserForbiddenResult>> SearchForbid(@RequestParam(required = true)
                                                             @NotNull(message = "关键词不能为空")
                                                             @NotEmpty(message = "关键词不能为空字符串")
                                                             @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                                             @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.searchUserForbidPage(keyword, pageNum, pageSize)));
    }

    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @ApiOperation(value = "手动更新举报信息", notes = "手动更新举报信息")
    @RequestMapping(value = "/report/update", method = RequestMethod.GET)
    public Msg UpdateReport() {
        countService.transUserReporterDataFromRedis2DB();
        countService.transBlogReportDataFromRedis2DB();
        return MsgUtil.ok(null);
    }
}
