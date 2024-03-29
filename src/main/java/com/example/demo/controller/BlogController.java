package com.example.demo.controller;

import com.example.demo.dto.BlogContentDTO;
import com.example.demo.dto.BlogDTO;
import com.example.demo.msgutils.Msg;
import com.example.demo.msgutils.MsgCode;
import com.example.demo.msgutils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.BadLocationException;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/blog")
@Api(tags="博文模块")
@RestController
public class BlogController {
    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddBlog(@ModelAttribute @Valid BlogContentDTO blogContentDTO) {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
    }

    @ApiOperation(value = "GET博文")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BlogDTO GetBlog(Integer blog_id) {
        return new BlogDTO();
    }
    @ApiOperation(value = "编辑博文")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Msg PutBlog(Integer blog_id,String text) {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
    }

    @ApiOperation(value = "删除博文")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Msg DeleteBlog(Integer blog_id) {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
    }

    @ApiOperation(value = "进行评论")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public void Comment(Integer blog_id, Integer comment_id, String text) {

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
