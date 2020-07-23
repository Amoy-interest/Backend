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
import com.example.amoy_interest.utils.CommonPage;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/blogs")
@Api(tags = "博文模块")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @UserLoginToken
    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddBlog(@RequestBody @Valid BlogContentDTO blogContentDTO, @RequestHeader(value = "token") String token) {
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
    @ApiOperation(value = "获取博文基本内容(不包括评论)")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<BlogDTO> GetBlog(@NotNull(message = "博文id不能为空")
                                @Min(value = 1, message = "id不能小于1") Integer blog_id) {
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, blogService.getAllBlogDetail(blog_id));
    }

    @UserLoginToken
    @ApiOperation(value = "编辑博文")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Msg PutBlog(@RequestBody @Valid BlogPutDTO blogPutDTO) {
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
    public Msg DeleteBlog(@NotNull(message = "博文id不能为空")
                          @Min(value = 1, message = "id不能小于1") Integer blog_id) {
        blogService.deleteByBlog_id(blog_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_BLOG_SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "进行评论")
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Msg Comment(@RequestHeader(value = "token") String token, @RequestBody @Valid CommentPostDTO commentPostDTO) {
        Integer blog_id = commentPostDTO.getBlog_id();
        Integer root_comment_id = commentPostDTO.getRoot_comment_id();
        Integer reply_user_id = commentPostDTO.getReply_user_id();
        String text = commentPostDTO.getText();
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();

        BlogComment blogComment = new BlogComment();
        blogComment.setBlog_id(blog_id);
        blogComment.setUser_id(user_id);
        if (root_comment_id == -1) {
            blogComment.setComment_level(1); //一级评论
        } else {
            blogComment.setComment_level(2); //二级评论
        }
        blogComment.setReply_user_id(reply_user_id);
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
    public Msg DeleteComment(@NotNull(message = "评论id不能为空")
                             @Min(value = 1, message = "评论id不能小于1") Integer comment_id) {
        blogService.deleteCommentByComment_id(comment_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_COMMENT_SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "以分页的形式获取一级评论")
    @GetMapping(value = "/comments/level1")
    public Msg<CommonPage<BlogCommentLevel1DTO>> GetLevel1Comments(@NotNull(message = "博文id不能为空")
                                                                   @Min(value = 1, message = "id不能小于1") Integer blog_id,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getLevel1CommentPage(blog_id, pageNum, pageSize)));
    }

    @UserLoginToken
    @ApiOperation(value = "以分页的形式获取多级评论")
    @GetMapping(value = "/comments/multilevel")
    public Msg<CommonPage<BlogCommentMultiLevelDTO>> GetMultiComments(@NotNull(message = "根评论id不能为空")
                                                                      @Min(value = 1, message = "根评论id不能小于1") Integer root_comment_id,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(blogService.getMultiLevelCommentPage(root_comment_id, pageNum, pageSize)));
    }

    //不支持修改评论
    @UserLoginToken
    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public Msg Vote(@RequestBody @Valid VoteDTO voteDTO) {
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
    public Msg CancelVote(@RequestBody @Valid VoteDTO voteDTO) { //用body还是在url上？
        Integer comment_id = voteDTO.getComment_id();
        Integer blog_id = voteDTO.getBlog_id();
        if (comment_id == -1) {
            blogService.decrVoteCount(blog_id);
        } else {
            blogService.decrCommentVoteCount(comment_id);
        }
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.CANCEL_VOTE_SUCCESS_MSG);
    }

//    //删除该api?
//    @UserLoginToken
//    @ApiOperation(value = "搜索全部内容(应该不会用到这个api)")
//    @RequestMapping(value = "/searchAll", method = RequestMethod.GET)
//    public Msg<List<BlogDTO>> SearchAll(String keyword) {
//        List<BlogDTO> blogDTOS = new ArrayList<>();
//        List<Blog> blogs = blogService.getAllBlogs();
//        for (Blog blog : blogs) {
//            if (blog.getBlog_text().contains(keyword)) {
//                blogDTOS.add(blogService.getSimpleBlogDetail(blog.getBlog_id()));
//            }
//        }
//        return new Msg(MsgCode.SUCCESS, MsgUtil.SEARCH_SUCCESS_MSG, blogDTOS);
//    }

    @UserLoginToken
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

    @UserLoginToken
    @ApiOperation(value = "分页获取推荐blog（未实现热度,现在等于未登录前的blog）")
    @GetMapping(value = "/recommend")
    public Msg<CommonPage<BlogDTO>> GetRecommendBlogs(@RequestHeader(value = "token") String token,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getAllBlogPageOrderByTime(pageNum, pageSize)));
    }

    @UserLoginToken
    @ApiOperation(value = "分页获取关注blog")
    @GetMapping(value = "/follow")
    public Msg<CommonPage<BlogDTO>> GetFollowBlogs(@RequestHeader(value = "token") String token,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer user_id = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getFollowBlogPageByUser_idOrderByTime(user_id, pageNum, pageSize)));
    }

    @UserLoginToken
    @ApiOperation(value = "分页获取某人(可以是自己也可以是他人)blog")
    @GetMapping(value = "/users")
    public Msg<CommonPage<BlogDTO>> GetUserBlogs(@RequestParam(required = true) Integer user_id,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                 @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getBlogPageByUser_idOrderByTime(user_id, pageNum, pageSize)));
    }

    @ApiOperation(value = "分页获取未登录前blog(未实现热度,暂时取最新的blog)")
    @GetMapping(value = "/beforeLogin")
    public Msg<CommonPage<BlogDTO>> GetBeforeLoginBlogs(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                        @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(blogService.getAllBlogPageOrderByTime(pageNum, pageSize)));
    }

    @ApiOperation(value = "举报博文")
    @PostMapping(value = "/report")
    public Msg ReportBlog(@NotNull(message = "博文id不能为空")
                          @Min(value = 1, message = "id不能小于1") Integer blog_id) {
        blogService.reportBlogByBlog_id(blog_id);
        return new Msg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

}
