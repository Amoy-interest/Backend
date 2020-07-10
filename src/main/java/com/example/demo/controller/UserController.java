package com.example.demo.controller;

import com.example.demo.annotation.UserLoginToken;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.msgutils.Msg;
import com.example.demo.msgutils.MsgCode;
import com.example.demo.msgutils.MsgUtil;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user")
@Api(tags="用户模块")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String")
    })
//    public UserDTO Login(String username, String password) {
//        User user = userService.findUserByUsername(username);
//        if(user == null) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.;
//        }
//        return new UserDTO();
//    }
    //测试版
    public Object login(String username,String password){
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findUserByUsername(username);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(password)){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    //用于测试
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
    @ApiOperation(value = "登出",notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }
    @ApiOperation(value = "注册",notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
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
