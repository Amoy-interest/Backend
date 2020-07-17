package com.example.amoy_interest.controller;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.annotation.UserLoginToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/blogs")
@Api(tags="博文模块")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @UserLoginToken
    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddBlog(@RequestBody BlogContentDTO blogContentDTO, @RequestHeader(value = "token") String token) {
        Blog blog = new Blog();
        blog.setUser_id(JWT.decode(token).getClaim("user_id").asInt());
        blog.setBlog_type(0);  //原创
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogContentDTO.getText());
        blog.set_deleted(false);
        blogService.addBlog(blog);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ADD_BLOG_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "GET博文")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<BlogDTO> GetBlog(Integer blog_id) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getAllBlogDetail(blog_id));
    }
    @UserLoginToken
    @ApiOperation(value = "编辑博文")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Msg PutBlog(@RequestBody BlogPutDTO blogPutDTO) {
        Integer blog_id = blogPutDTO.getBlog_id();
        String text = blogPutDTO.getText();
        Blog blog = blogService.findBlogByBlog_id(blog_id);
        blog.setBlog_text(text);
        blogService.updateBlog(blog);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.PUT_BLOG_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "删除博文")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Msg DeleteBlog(Integer blog_id) {
        blogService.deleteByBlog_id(blog_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_BLOG_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "进行评论")
    @RequestMapping(value = "/comments",method = RequestMethod.POST)
    public Msg Comment(@RequestBody CommentPostDTO commentPostDTO) {
        Integer blog_id = commentPostDTO.getBlog_id();
        Integer root_comment_id = commentPostDTO.getRoot_comment_id();
        String reply_comment_nickname = commentPostDTO.getReply_comment_nickname();
        String text = commentPostDTO.getText();
        String nickname = commentPostDTO.getNickname();

        BlogComment blogComment = new BlogComment();
        blogComment.setBlog_id(blog_id);
        blogComment.setNickname(nickname);
        if (root_comment_id == -1) blogComment.setComment_level(1); //一级评论
        else {
            blogComment.setComment_level(2); //二级评论
            blogComment.setReply_comment_nickname(reply_comment_nickname);
        }
        blogComment.setComment_text(text);
        blogComment.setComment_time(new Date());
        blogComment.setVote_count(0);
        blogComment.set_deleted(false);
        blogComment.setRoot_comment_id(root_comment_id);
        blogService.addBlogComment(blogComment);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.COMMENT_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "删除评论")
    @DeleteMapping(value = "/comments")
    public Msg DeleteComment(Integer comment_id) {
        blogService.deleteCommentByComment_id(comment_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_COMMENT_SUCCESS_MSG);
    }
    //不支持修改评论
    @UserLoginToken
    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public Msg Vote(@RequestBody VoteDTO voteDTO) {
        Integer comment_id = voteDTO.getComment_id();
        Integer blog_id = voteDTO.getBlog_id();
        if (comment_id == -1) {
            blogService.incrVoteCount(blog_id);
        } else {
            blogService.incrCommentVoteCount(comment_id);
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.VOTE_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "取消点赞")
    @DeleteMapping(value = "/vote")
    public Msg CancelVote(@RequestBody VoteDTO voteDTO) { //用body还是在url上？
        Integer comment_id = voteDTO.getComment_id();
        Integer blog_id = voteDTO.getBlog_id();
        if (comment_id == -1) {
            blogService.decrVoteCount(blog_id);
        } else {
            blogService.decrCommentVoteCount(comment_id);
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.CANCEL_VOTE_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Msg<List<BlogDTO>> Search(String keyword){
        List<BlogDTO> blogDTOS = new ArrayList<>();
        List<Blog> blogs = blogService.getAllBlogs();
        for (Blog blog : blogs) {
            if (blog.getBlog_text().contains(keyword)) {
                blogDTOS.add(blogService.getSimpleBlogDetail(blog.getBlog_id()));
            }
        }
        return new Msg(MsgCode.SUCCESS, MsgUtil.SEARCH_SUCCESS_MSG, blogDTOS);
    }

    @UserLoginToken
    @ApiOperation(value = "获取推荐blog")
    @GetMapping(value = "/recommend")
    public Msg<List<BlogDTO>> GetRecommendBlogs(@RequestHeader(value = "token") String token) {
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getRecommendBlogsByUser_id(user_id));
    }
    @UserLoginToken
    @ApiOperation(value = "获取关注blog")
    @GetMapping(value = "/follow")
    public Msg<List<BlogDTO>> GetFollowBlogs(@RequestHeader(value = "token") String token) {
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getFollowBlogsByUser_id(user_id));
    }
    @UserLoginToken
    @ApiOperation(value = "获取自己blog")
    @GetMapping(value = "/own")
    public Msg<List<BlogDTO>> GetOwnBlogs(@RequestHeader(value = "token") String token) {
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getBlogsByUser_id(user_id));
    }

    @ApiOperation(value = "获取未登录前blog")
    @GetMapping(value = "/beforeLogin")
    public Msg<List<BlogDTO>> GetBeforeLoginBlogs() {
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getRecommendBlogsByUser_id(1));
    }
}