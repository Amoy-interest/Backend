package com.example.amoy_interest.controller;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.constant.Constant;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.exception.CustomException;
import com.example.amoy_interest.exception.CustomUnauthorizedException;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequestMapping("/users")
@Api(tags = "用户模块")
@RestController
@PropertySource("classpath:config.properties")
public class UserController {
    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;
    @Autowired
    private UserService userService;
    @Autowired
    private UserUtil userUtil;

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg<UserInfoDTO> Login(@RequestBody @Valid LoginDTO data, HttpServletResponse httpServletResponse) {
        UserAuth userAuth = userService.findUserAuthByUsername(data.getUsername());
        if (userAuth == null) {
            throw new CustomUnauthorizedException("该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userAuth.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(data.getUsername() + data.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + data.getUsername())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + data.getUsername());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + data.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userAuth.getUser_id(), data.getUsername(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new Msg(HttpStatus.OK.value(), "登录成功(Login Success.)", new UserInfoDTO(userAuth.getUser(), false));
        } else {
            throw new CustomUnauthorizedException("帐号或密码错误(Account or Password Error.)");
        }
//        String username = data.getUsername();
//        String password = data.getPassword();
//        //需要重写，用checkUser,检查用户是否被封号
//        UserAuth userAuth = userService.findUserAuthByUsername(username);
//        if (userAuth == null) {
//            return new Msg<UserDTO>(MsgCode.USER_NOT_EXIST, MsgUtil.LOGIN_USER_ERROR_MSG, null);
//        } else {
//            if (!userAuth.getPassword().equals(password)) {
//                return new Msg<UserDTO>(MsgCode.ERROR, MsgUtil.LOGIN_USER_ERROR_MSG, null);
//            } else {
//                if (userAuth.getIs_forbidden() == 1) {
//                    return new Msg<>(MsgCode.LOGIN_USER_ERROR, MsgUtil.USER_FORBIDDEN_MSG);
//                }
//                String token = tokenService.getToken(userAuth);
//                UserInfoDTO userInfoDTO = new UserInfoDTO(userAuth.getUser(), false);
//                UserDTO userDTO = new UserDTO(userInfoDTO, token);
//                return new Msg<UserDTO>(MsgCode.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, userDTO);
//            }
//        }
    }


    @RequiresAuthentication
    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(value = "/logout")
    public Msg Logout() {
        String username = userUtil.getUsername();
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
            if (JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username) > 0) {
                return new Msg(HttpStatus.OK.value(), "登出成功(Logout Success)", null);
            }
        }
        throw new CustomException("登出失败，Username不存在(Logout Failed. Username does not exist.)");
    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg<UserInfoDTO> Register(@RequestBody @Valid RegisterDTO registerDTO, HttpServletResponse httpServletResponse) {
//        UserDto userDtoTemp = new UserDto();
//        userDtoTemp.setAccount(userDto.getAccount());
//        userDtoTemp = userService.selectOne(userDtoTemp);
        String username = registerDTO.getUsername();
        UserAuth userAuth = userService.findUserAuthByUsername(username);
        if (userAuth != null) {
            throw new CustomUnauthorizedException("该帐号已存在(Account exist.)");
        }
//        userDto.setRegTime(new Date());
        // 密码以帐号+密码的形式进行AES加密
//        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
//            throw new CustomException("密码最多8位(Password up to 8 bits.)");
//        }
        String key = AesCipherUtil.enCrypto(registerDTO.getUsername() + registerDTO.getPassword());
        registerDTO.setPassword(key);
        UserInfoDTO userInfoDTO = userService.register(registerDTO);
//        if (count <= 0) {
//            throw new CustomException("新增失败(Insert Failure)");
//        }
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(userInfoDTO.getUser_id(), username, currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return new Msg(HttpStatus.OK.value(), "注册成功(Insert Success)", userInfoDTO);
//        String username = registerDTO.getUsername();
//        UserAuth userAuth = userService.findUserAuthByUsername(username);
//        if (userAuth != null) {
//            return new Msg<UserDTO>(MsgCode.USER_EXIST, MsgUtil.USER_EXIST_MSG, null);
//        }
//        UserInfoDTO userInfoDTO = userService.register(registerDTO);
//        userAuth = userService.findUserAuthByUsername(username);
//        if (userInfoDTO == null) {
//            return new Msg<UserDTO>(MsgCode.ERROR, MsgUtil.SIGNIN_ERR_MSG, null);
//        }
//        String token = tokenService.getToken(userAuth);
//        UserDTO userDTO = new UserDTO(userInfoDTO, token);
//        return new Msg<UserDTO>(MsgCode.SUCCESS, MsgUtil.REGISTER_SUCCESS_MSG, userDTO);
    }

    @RequiresAuthentication
    @ApiOperation(value = "关注用户", notes = "关注")
    @PostMapping(value = "/follow")
    public Msg Follow(@NotNull(message = "关注id不能为空")
                      @Min(value = 1, message = "关注id不能小于1")
                      @RequestParam(required = true) Integer follow_id) {
        Integer userId = userUtil.getUserId();
        userService.follow(userId, follow_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }

    @RequiresAuthentication
    @ApiOperation(value = "取关用户", notes = "取关")
    @PostMapping(value = "/unfollow")
    public Msg UnFollow(@NotNull(message = "取关id不能为空")
                        @Min(value = 1, message = "取关id不能小于1")
                        @RequestParam Integer follow_id) {
        Integer userId = userUtil.getUserId();
        userService.unfollow(userId, follow_id);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG);
    }


    @RequiresAuthentication
    @ApiOperation(value = "分页获取（自己或他人）关注列表")
    @GetMapping(value = "/follow")
    public Msg<CommonPage<UserInfoDTO>> GetFollow(@NotNull(message = "id不能为空")
                                                  @Min(value = 1, message = "id不能小于1")
                                                  @RequestParam(required = true) Integer user_id,
                                                  @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                  @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer my_user_id = userUtil.getUserId();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserFollowPage(my_user_id, user_id, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "分页获取(自己或他人)粉丝列表")
    @GetMapping(value = "/fans")
    public Msg<CommonPage<UserInfoDTO>> GetFan(@NotNull(message = "id不能为空")
                                               @Min(value = 1, message = "id不能小于1")
                                               @RequestParam(required = true) Integer user_id,
                                               @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Integer my_user_id = userUtil.getUserId();
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, CommonPage.restPage(userService.getUserFanPage(my_user_id, user_id, pageNum, pageSize)));
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "")
    public Msg<UserDTO> GetUserInfo(@RequestParam(required = true) Integer user_id) {
        return new Msg<>(MsgCode.SUCCESS, MsgUtil.SUCCESS_MSG, userService.getUserDTO(userUtil.getUserId(), user_id));
    }

    @ApiOperation(value = "根据账号密码计算出加密的密码（手动存入数据库）")
    @GetMapping(value = "/cal")
    public Msg<String> CalculatePassword(@RequestParam(required = true) String username,
                                         @RequestParam(required = true) String password) {
        return new Msg(HttpStatus.OK.value(), "计算成功", AesCipherUtil.enCrypto(username + password));
    }
}
