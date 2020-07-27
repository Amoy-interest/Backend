package com.example.amoy_interest.controller;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.annotation.UserLoginToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.entity.UserFollow;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.TokenService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.CommonPage;
//import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/users")
@Api(tags = "用户模块")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg<UserDTO> Login(@RequestBody @Valid LoginDTO data) {
        String username = data.getUsername();
        String password = data.getPassword();
        //需要重写，用checkUser,检查用户是否被封号
        UserAuth userAuth = userService.findUserAuthByUsername(username);
        if (userAuth == null) {
            return new Msg<UserDTO>(MsgCode.USER_NOT_EXIST, MsgUtil.LOGIN_USER_ERROR_MSG, null);
        } else {
            if (!userAuth.getPassword().equals(password)) {
                return new Msg<UserDTO>(MsgCode.ERROR, MsgUtil.LOGIN_USER_ERROR_MSG, null);
            } else {
                if (userAuth.getIs_forbidden() == 1) {
                    return new Msg<>(MsgCode.LOGIN_USER_ERROR, MsgUtil.USER_FORBIDDEN_MSG);
                }
                String token = tokenService.getToken(userAuth);
                UserInfoDTO userInfoDTO = new UserInfoDTO(userAuth.getUser(), false);
                UserDTO userDTO = new UserDTO(userInfoDTO, token);
                return new Msg<UserDTO>(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, userDTO);
            }
        }
    }


    //登出后台不需要做什么，直接前台把token删掉就好了
    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.LOGOUT_SUCCESS_MSG);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg<UserDTO> Register(@RequestBody @Valid RegisterDTO registerDTO) { //@ModelAttribute @Valid ?检验有效性？
        String username = registerDTO.getUsername();
        UserAuth userAuth = userService.findUserAuthByUsername(username);
        if (userAuth != null) {
            return new Msg<UserDTO>(MsgCode.USER_EXIST, MsgUtil.USER_EXIST_MSG, null);
        }
        UserInfoDTO userInfoDTO = userService.register(registerDTO);
        userAuth = userService.findUserAuthByUsername(username);
        if (userInfoDTO == null) {
            return new Msg<UserDTO>(MsgCode.ERROR, MsgUtil.SIGNIN_ERR_MSG, null);
        }
        String token = tokenService.getToken(userAuth);
        UserDTO userDTO = new UserDTO(userInfoDTO, token);
        return new Msg<UserDTO>(MsgCode.SUCCESS, MsgUtil.REGISTER_SUCCESS_MSG, userDTO);
    }

    @UserLoginToken
    @ApiOperation(value = "关注用户", notes = "关注")
    @PostMapping(value = "/follow")
    public Msg Follow(@NotNull(message = "关注id不能为空")
                      @Min(value = 1, message = "关注id不能小于1")
                      @RequestParam(required = true) Integer follow_id,
                      @RequestHeader(value = "token") String token) {
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
        userService.follow(userId, follow_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @UserLoginToken
    @ApiOperation(value = "取关用户", notes = "取关")
    @PostMapping(value = "/unfollow")
    public Msg UnFollow(@NotNull(message = "取关id不能为空")
                        @Min(value = 1, message = "取关id不能小于1")
                        @RequestParam Integer follow_id,
                        @RequestHeader(value = "token") String token) {
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
        userService.unfollow(userId, follow_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }


    @UserLoginToken
    @ApiOperation(value = "分页获取关注列表")
    @GetMapping(value = "/follow")
    public Msg<CommonPage<UserInfoDTO>> GetFollow(@RequestHeader(value = "token") String token,
                                                  @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                  @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserFollowPage(userId, pageNum, pageSize)));
    }

    @UserLoginToken
    @ApiOperation(value = "分页获取粉丝列表")
    @GetMapping(value = "/fans")
    public Msg<CommonPage<UserInfoDTO>> GetFan(@RequestHeader(value = "token") String token,
                                               @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer userId = JWT.decode(token).getClaim("user_id").asInt();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserFanPage(userId, pageNum, pageSize)));
    }

    @UserLoginToken
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "")
    public Msg<UserInfoDTO> GetUserInfo(@RequestHeader(value = "token") String token,
                                        @RequestParam(required = true) Integer user_id) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, userService.getUserInfo(JWT.decode(token).getClaim("user_id").asInt(), user_id));
    }
}
