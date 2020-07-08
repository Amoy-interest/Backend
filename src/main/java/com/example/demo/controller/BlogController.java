package com.example.demo.controller;

import com.example.demo.dto.BlogContentDTO;
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

@RequestMapping("/blog")
@Api(tags="博文模块")
@RestController
public class BlogController {
    @ApiOperation(value = "写博文")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddBlog(@ModelAttribute @Valid BlogContentDTO blogContentDTO) {
        JSONObject data = JSONObject.fromObject(blogContentDTO);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, data);
    }

    @ApiOperation(value = "GET博文")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg GetBlog(Integer blog_id) {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, null);
    }



}
