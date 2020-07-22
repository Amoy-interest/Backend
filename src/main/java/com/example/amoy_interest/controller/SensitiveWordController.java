package com.example.amoy_interest.controller;

import com.example.amoy_interest.annotation.UserLoginToken;
import com.example.amoy_interest.entity.SensitiveWord;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.SensitiveWordService;
import com.example.amoy_interest.utils.CommonPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/keywords")
@Api(tags="敏感词模块")
@RestController
public class SensitiveWordController {
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @UserLoginToken
    @ApiOperation(value = "以分页的方式获取敏感词列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Msg<CommonPage<SensitiveWord>> GetSensitiveWords(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<SensitiveWord> sws = sensitiveWordService.getSensitiveWordsPage(pageNum,pageSize);
        return new Msg(MsgCode.SUCCESS, MsgUtil.GET_BLOG_SUCCESS_MSG, CommonPage.restPage(sws));
    }
    @UserLoginToken
    @ApiOperation(value = "添加敏感词")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg AddSensitiveWord(String keyword) {
        System.out.println(keyword);
        sensitiveWordService.addSensitiveWord(new SensitiveWord(keyword));
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ADD_BLOG_SUCCESS_MSG);
    }
    //有bug，不能修改主键？
    @UserLoginToken
    @ApiOperation(value = "编辑敏感词")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Msg PutSensitiveWord(String oldWord, String newWord) {
        SensitiveWord sensitiveWord = sensitiveWordService.findSensitiveWordByKeyword(oldWord);
        sensitiveWord.setKeyword(newWord);
        sensitiveWordService.updateSensitiveWord(sensitiveWord);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.PUT_BLOG_SUCCESS_MSG);
    }
    @UserLoginToken
    @ApiOperation(value = "删除敏感词")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Msg DeleteSensitiveWord(String keyword) {
        System.out.println(keyword);
        sensitiveWordService.deleteByKeyword(keyword);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_BLOG_SUCCESS_MSG);
    }
}
