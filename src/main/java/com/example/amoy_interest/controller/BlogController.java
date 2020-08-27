package com.example.amoy_interest.controller;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.*;
import com.example.amoy_interest.utils.CommonPage;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageInfo;
import com.example.amoy_interest.utils.UserUtil;
//import com.example.amoy_interest.utils.sensitivefilter.WordFilter;
import com.example.amoy_interest.utils.sensitivefilter2.FinderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping("/blogs")
@Api(tags = "博文模块")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserUtil userUtil;

    @RequiresAuthentication
    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Msg<BlogDTO> AddBlog(@RequestBody @Valid BlogAddDTO blogAddDTO) {
    public Msg AddBlog(@RequestBody @Valid BlogAddDTO blogAddDTO) {
//        Integer user_id = userUtil.getUserId();
//        UserAuth userAuth = userService.findUserAuthById(user_id);
        UserAuth userAuth = userUtil.getUser();//已经做了判断是否为空的处理
//        if (userAuth == null) {
//            return new Msg<>(MsgCode.ERROR, MsgUtil.USER_NOT_EXIST_MSG);
//        }
        if (userAuth.getIs_ban() == 1 && userAuth.getUserBan().getBan_time().after(new Date())) {//应该先去判断封号时间是否结束
            return new Msg<>(402, MsgUtil.USER_BAN_MSG);
        }
        //测试一下两种方法哪个更高效
//        if(WordFilter.isContains(blogAddDTO.getText()))
//            return new Msg<>(403,"内容含有敏感词，请修改");
        Set<String> stringSet = FinderUtil.find(blogAddDTO.getText());
        if(!stringSet.isEmpty())
            return new Msg<>(402,"内容含有敏感词",stringSet);
        blogAddDTO.setUser_id(userAuth.getUser_id());
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.ADD_BLOG_SUCCESS_MSG, blogService.addBlog(blogAddDTO));
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取博文基本内容(不包括评论)")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<BlogDTO> GetBlog(@NotNull(message = "博文id不能为空")
                                @Min(value = 1, message = "id不能小于1")
                                @RequestParam(required = true) Integer blog_id) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getAllBlogDetail(blog_id));
    }


    @RequiresAuthentication
    @ApiOperation(value = "编辑博文")
    @RequestMapping(value = "", method = RequestMethod.PUT)
//    public Msg<BlogDTO> PutBlog(@RequestBody @Valid BlogPutDTO blogPutDTO) {
    public Msg PutBlog(@RequestBody @Valid BlogPutDTO blogPutDTO) {
            blogPutDTO.setImages(null);
        UserAuth userAuth = userUtil.getUser();
        if (userAuth.getIs_ban() == 1 && userAuth.getUserBan().getBan_time().after(new Date())) {//应该先去判断封号时间是否结束
            return new Msg<>(402, MsgUtil.USER_BAN_MSG);
        }
        Set<String> stringSet = FinderUtil.find(blogPutDTO.getText());
        if(!stringSet.isEmpty())
            return new Msg<>(402,"内容含有敏感词",stringSet);
//        if(WordFilter.isContains(blogPutDTO.getText()))
//            return new Msg<>(403,"内容含有敏感词，请修改");
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.PUT_BLOG_SUCCESS_MSG, blogService.updateBlog(blogPutDTO));
    }

    @RequiresAuthentication
    @ApiOperation(value = "转发")
    @PostMapping(value = "/forward")
    public Msg ForwardBlog(@RequestBody @Valid BlogForwardDTO blogForwardDTO) {
        UserAuth userAuth = userUtil.getUser();
        if (userAuth.getIs_ban() == 1 && userAuth.getUserBan().getBan_time().after(new Date())) {
            return new Msg<>(MsgCode.ERROR, MsgUtil.USER_BAN_MSG);
        }
        Set<String> stringSet = FinderUtil.find(blogForwardDTO.getText());
        if(!stringSet.isEmpty())
            return new Msg<>(402,"内容含有敏感词",stringSet);
//        if(WordFilter.isContains(blogForwardDTO.getText()))
//            return new Msg<>(403,"内容含有敏感词，请修改");
        blogForwardDTO.setUser_id(userAuth.getUser_id());
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, blogService.forwardBlog(blogForwardDTO));
    }

    @RequiresAuthentication
    @ApiOperation(value = "删除博文")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Msg DeleteBlog(@NotNull(message = "博文id不能为空")
                          @Min(value = 1, message = "id不能小于1")
                          @RequestParam(required = true) Integer blog_id) {
        if(blogService.deleteByBlog_id(blog_id) == 0) {
            return MsgUtil.makeMsg(MsgCode.ERROR,"你无权删除");
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_BLOG_SUCCESS_MSG);
    }

    @RequiresAuthentication
    @ApiOperation(value = "进行评论")
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Msg<BlogCommentMultiLevelDTO> Comment(@RequestBody @Valid CommentPostDTO commentPostDTO) {
        commentPostDTO.setUser_id(userUtil.getUserId());
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.COMMENT_SUCCESS_MSG, blogService.addBlogComment(commentPostDTO));
    }

    //删除评论就不计数了
    @RequiresAuthentication
    @ApiOperation(value = "删除评论")
    @DeleteMapping(value = "/comments")
    public Msg DeleteComment(@NotNull(message = "评论id不能为空")
                             @Min(value = 1, message = "评论id不能小于1")
                             @RequestParam(required = true) Integer comment_id) {
        blogService.deleteCommentByComment_id(comment_id);//
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_COMMENT_SUCCESS_MSG);
    }

    @RequiresAuthentication
    @ApiOperation(value = "以分页的形式获取一级评论")
    @GetMapping(value = "/comments/level1")
    public Msg<CommonPage<BlogCommentLevel1DTO>> GetLevel1Comments(@NotNull(message = "博文id不能为空")
                                                                   @Min(value = 1, message = "id不能小于1")
                                                                   @RequestParam(required = true) Integer blog_id,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getLevel1CommentPage(blog_id, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "以分页的形式获取多级评论")
    @GetMapping(value = "/comments/multilevel")
    public Msg<CommonPage<BlogCommentMultiLevelDTO>> GetMultiComments(@NotNull(message = "根评论id不能为空")
                                                                      @Min(value = 1, message = "根评论id不能小于1")
                                                                      @RequestParam(required = true) Integer root_comment_id,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getMultiLevelCommentPage(root_comment_id, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public Msg Vote(@RequestBody @Valid VoteDTO voteDTO) {
        Integer comment_id = voteDTO.getComment_id();
        Integer blog_id = voteDTO.getBlog_id();
        if (comment_id == 0) {
            blogService.incrVoteCount(blog_id);
        } else {
            //评论的点赞也得处理
            blogService.incrCommentVoteCount(comment_id);
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.VOTE_SUCCESS_MSG);
    }

    @RequiresAuthentication
    @ApiOperation(value = "取消点赞")
    @DeleteMapping(value = "/vote")
    public Msg CancelVote(@RequestBody @Valid VoteDTO voteDTO) { //用body还是在url上？
        Integer comment_id = voteDTO.getComment_id();
        Integer blog_id = voteDTO.getBlog_id();
        if (comment_id == 0) {
            blogService.decrVoteCount(blog_id);
        } else {
            blogService.decrCommentVoteCount(comment_id);
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.CANCEL_VOTE_SUCCESS_MSG);
    }


    @RequiresAuthentication
    @ApiOperation(value = "搜索以分页的形式展示")
    @GetMapping(value = "/search")//Msg<CommonPage<BlogDTO>
    public Msg<CommonPage<BlogDTO>> Search(@RequestParam(required = true)
                                           @NotNull(message = "关键词不能为空")
                                           @NotEmpty(message = "关键词不能为空字符串")
                                           @Length(max = 40, message = "关键词不能大于40位") String keyword,
                                           @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SEARCH_SUCCESS_MSG, CommonPage.restPage(blogService.getSearchListByBlog_text(keyword, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "分页获取推荐blog（未实现热度,现在等于未登录前的blog）")
    @GetMapping(value = "/recommend")
    public Msg<CommonPage<BlogDTO>> GetRecommendBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer user_id = userUtil.getUserId();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getAllBlogPageOrderByTime(pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "分页获取关注blog")
    @GetMapping(value = "/follow")
    public Msg<CommonPage<BlogDTO>> GetFollowBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer user_id = userUtil.getUserId();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getFollowBlogPageByUser_idOrderByTime(user_id, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "分页获取某人(可以是自己也可以是他人)blog")
    @GetMapping(value = "/users")
    public Msg<CommonPage<BlogDTO>> GetUserBlogs(@RequestParam(required = false) Integer user_id,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                 @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        if(orderType == 0) {
            return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getBlogPageByUser_idOrderByTime(user_id, pageNum, pageSize)));
        }
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG,CommonPage.restPage(blogService.getHotBlogPageByUser_id(user_id,pageNum,pageSize)));
    }

    @ApiOperation(value = "分页获取未登录前blog(热度最高的博文)")
    @GetMapping(value = "/beforeLogin")
    public Msg<CommonPage<BlogDTO>> GetBeforeLoginBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                        @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getBlogPageOrderByHot(pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "举报博文")
    @PostMapping(value = "/report")
    public Msg ReportBlog(@RequestBody @Valid BlogReportDTO blogReportDTO) {
        blogService.reportBlog(blogReportDTO);

        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }
    @GetMapping(value = "/test")
    public void test(){
        blogService.insertToES();
    }
}
