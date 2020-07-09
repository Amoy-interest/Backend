package com.example.demo.controller;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.msgutils.Msg;
import com.example.demo.msgutils.MsgCode;
import com.example.demo.msgutils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user")
@Api(tags="用户模块")
@RestController
public class UserController {
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String")
    })
    public UserDTO Login(String username, String password) {
        return new UserDTO();
    }

    @ApiOperation(value = "登出",notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }
    @ApiOperation(value = "注册",notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "query", name = "address", value = "地址", required = true, dataType = "String")
//    })
//    public Msg Register(String username,String password,String email,String sex,String address) {
//    public Msg Register(@ModelAttribute @Valid RegisterDTO registerDTO){
//        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.REGISTER_SUCCESS_MSG);
//    }
    public UserDTO Register(@ModelAttribute @Valid RegisterDTO registerDTO){
        return new UserDTO();
    }

    @ApiOperation(value = "用户检查",notes = "用户检查")
    @GetMapping(value = "/check")
    public Msg Check() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
    }

    @ApiOperation(value = "关注用户",notes = "关注")
    @PostMapping(value = "/follow")
    public void Follow(String follow_id) {

    }

}
