package com.example.amoy_interest.controller;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.annotation.UserLoginToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.TokenService;
import com.example.amoy_interest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@Api(tags="用户模块")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg<UserDTO> Login(@RequestBody LoginDTO data) {
        String username = data.getUsername();
        String password = data.getPassword();
        //需要重写，用checkUser,检查用户是否被封号
        UserAuth userAuth = userService.findUserAuthByUsername(username);

        if(userAuth == null) {
            return new Msg<UserDTO>(MsgCode.USER_NOT_EXIST,MsgUtil.LOGIN_USER_ERROR_MSG,null);
        }else {
            if(!userAuth.getPassword().equals(password)) {
                return new Msg<UserDTO>(MsgCode.ERROR,MsgUtil.LOGIN_USER_ERROR_MSG,null);
            }else {
                String token = tokenService.getToken(userAuth);
                UserInfoDTO userInfoDTO = new UserInfoDTO(userAuth.getUser());
                UserDTO userDTO = new UserDTO(userInfoDTO,token);
                return new Msg<UserDTO>(MsgCode.SUCCESS,MsgUtil.LOGIN_SUCCESS_MSG,userDTO);
            }
        }
    }



    //登出后台不需要做什么，直接前台把token删掉就好了
    @ApiOperation(value = "登出",notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }

    @ApiOperation(value = "注册",notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg<UserDTO> Register( @RequestBody RegisterDTO registerDTO){ //@ModelAttribute @Valid ?检验有效性？
        String username = registerDTO.getUsername();
        UserAuth userAuth = userService.findUserAuthByUsername(username);
        if(userAuth != null) {
            return new Msg<UserDTO>(MsgCode.USER_EXIST,MsgUtil.USER_EXIST_MSG,null);
        }
        //服务层验证密码和邮箱格式？
        UserInfoDTO userInfoDTO = userService.register(registerDTO);
        userAuth = userService.findUserAuthByUsername(username);
        if(userInfoDTO == null) {
            return new Msg<UserDTO>(MsgCode.ERROR,MsgUtil.SIGNIN_ERR_MSG,null);
        }
        String token = tokenService.getToken(userAuth);
        UserDTO userDTO = new UserDTO(userInfoDTO,token);
        return new Msg<UserDTO>(MsgCode.SUCCESS,MsgUtil.REGISTER_SUCCESS_MSG,userDTO);
    }

    //是否还需要check？
//    @ApiOperation(value = "用户检查",notes = "用户检查")
//    @GetMapping(value = "/check")
//    public Msg Check() {
//        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG);
//    }
    @UserLoginToken
    @ApiOperation(value = "关注用户",notes = "关注")
    @PostMapping(value = "/follow")
    public Msg Follow(Integer follow_id,@RequestHeader(value="token") String token) {
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
        userService.follow(userId,follow_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS,MsgUtil.SUCCESS_MSG);
    }

}
