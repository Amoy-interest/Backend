package com.example.demo.controller;

import com.auth0.jwt.JWT;
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
    private UserService userService;
    @Autowired
    private TokenService tokenService;
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
    public String getMessage(@RequestHeader(value="token") String token){
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
//        Integer userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));

        return "你的用户id为"+Integer.toString(userId);
    }


    //登出后台不需要做什么，直接前台把token删掉就好了
    @ApiOperation(value = "登出",notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }

    @ApiOperation(value = "注册",notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserDTO Register(@ModelAttribute @Valid RegisterDTO registerDTO){
        User user = userService.findUserByUsername(registerDTO.getUsername());
        if(user != null) {
            return new UserDTO(null,MsgUtil.makeMsg(MsgCode.USER_EXIST,MsgUtil.USER_EXIST_MSG));
        }
        //后台验证密码和邮箱格式？
        userService.register(registerDTO);
        return new UserDTO();
    }

    //是否还需要check？
    @ApiOperation(value = "用户检查",notes = "用户检查")
    @GetMapping(value = "/check")
    public Msg Check() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
    }

    @ApiOperation(value = "关注用户",notes = "关注")
    @PostMapping(value = "/follow")
    public Msg Follow(String follow_id) {
        return null;
    }

}
