package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.dto.BlogContentDTO;
import com.example.demo.dto.BlogDTO;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.msgutils.Msg;
import com.example.demo.msgutils.MsgCode;
import com.example.demo.msgutils.MsgUtil;
import com.example.demo.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.BadLocationException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/blog")
@Api(tags="博文模块")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddBlog(@ModelAttribute @Valid BlogContentDTO blogContentDTO, @RequestHeader(value = "token") String token) {
        Blog blog = new Blog();
        blog.setUser_id(JWT.decode(token).getClaim("user_id").asInt());  //暂时不知道怎么获取user_id
        blog.setBlog_type(0);  //原创
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogContentDTO.getText());
        blog.set_deleted(false);
        blog.setBlog_image(blogContentDTO.getImages().get(0)); //是否应该调整数据库结构
        blogService.addBlog(blog);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ADD_BLOG_SUCCESS_MSG);
    }

    @ApiOperation(value = "GET博文")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BlogDTO GetBlog(Integer blog_id) {
        return new BlogDTO();
    }

    @ApiOperation(value = "编辑博文")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Msg PutBlog(Integer blog_id,String text) {
        Blog blog = blogService.findBlogByBlog_id(blog_id);
        blog.setBlog_text(text);
        blogService.updateBlog(blog);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.PUT_BLOG_SUCCESS_MSG);
    }

    @ApiOperation(value = "删除博文")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Msg DeleteBlog(Integer blog_id) {
        blogService.deleteByBlog_id(blog_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_BLOG_SUCCESS_MSG);
    }

    @ApiOperation(value = "进行评论")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public void Comment(Integer blog_id, Integer comment_id, String text) {
        BlogComment blogComment = new BlogComment();
        blogComment.setBlog_id(blog_id);
        //username
        blogComment.setUsername("0"); // 暂时不知道怎么获取user_id
        if (comment_id == -1) blogComment.setComment_level(1); //一级评论
        else {
            blogComment.setComment_level(2); //二级评论
            // reply_comment_username 是否应该增加参数
        }
        blogComment.setComment_text(text);
        blogComment.setComment_time(new Date());
        blogComment.setVote_count(0);
        blogComment.set_deleted(false);
        blogComment.setRoot_comment_id(comment_id);
    }

    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public void Vote(Integer blog_id,Integer comment_id) {

    }

    @ApiOperation(value = "搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<BlogDTO> Search(String keyword){
        return null;
    }
}
