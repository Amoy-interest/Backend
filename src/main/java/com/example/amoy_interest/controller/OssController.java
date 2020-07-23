package com.example.amoy_interest.controller;

import com.example.amoy_interest.dto.OssCallbackResult;
import com.example.amoy_interest.dto.OssPolicyResult;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.serviceimpl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;
    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public Msg<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG,result);
    }
    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Msg<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG,ossCallbackResult);
    }
}

