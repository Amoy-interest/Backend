package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.amoy_interest.constant.Constant.TEST_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    private MockMvc mockMvc;
    private final static String token1 = TEST_TOKEN;

    private static User user = new User(1, "mok", "mokkkkk@sjtu.edu.cn", 0, "上海市闵行区", 100, "啥都不会", null);
    private static User user2 = new User(1, "mok", "mokkkkk@sjtu.edu.cn", 0, "上海市闵行区", 100, "啥都不会", null);
    private static UserAuth userAuth = new UserAuth(1, "鲁迅", "NkRDOThDQkU4NUMwRDA5NkRENjY1QjhFMDQzRTZBRjk=", 0, 0, 0, user, null);
    private static UserAuth userAuth2 = new UserAuth(1, "鲁迅", "NkRDOThDQkU4NUMwRDA5NkRENjY1QjhFMDQzRTZBRjk=", 0, 1, 1, user, null);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SecurityManager securityManager;

    @MockBean
    private UserService userService;

    @MockBean
    private UserUtil userUtil;

    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private RecommendService recommendService;

    @BeforeAll
    public void init() {
        user.setUserAuth(userAuth);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime();
        userAuth2.setUserBan(new UserBan(1, date, date));
        user.setUserAuth(userAuth2);
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(context.getServletContext());
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(context.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Subject subject = new WebSubject
                .Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        JwtToken token = new JwtToken(TEST_TOKEN);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Test
    public void testLogin() throws Exception {
//        user.setUserAuth(userAuth);
//        UserInfoDTO userInfoDTO = new UserInfoDTO(100,"mok",0,"上海市闵行区","啥都不会",null,0,false);
//        when(userService.findUserAuthByUsername("admin")).thenReturn(userAuth);
        LoginDTO loginDTO = new LoginDTO("鲁迅", "123456");
        //登录无相关用户情况
        when(userService.findUserAuthByUsername(any())).thenReturn(null);
        String requestJson = JSONObject.toJSONString(loginDTO);
        MvcResult result = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
//        Msg<UserInfoDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserInfoDTO>>() {});
//        System.out.println(msg.getMsg());

        //登录账号密码正确情况
        when(userService.findUserAuthByUsername(any())).thenReturn(userAuth);
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andExpect(status().isOk())
                .andReturn();

        //登录账号被封号情况
        when(userService.findUserAuthByUsername(any())).thenReturn(userAuth2);
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        //登录账号密码不正确
        loginDTO.setPassword("1234567");
        requestJson = JSONObject.toJSONString(loginDTO);
        when(userService.findUserAuthByUsername(any())).thenReturn(userAuth);
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.LOGIN_SUCCESS_MSG,msg.getMsg());
//        assertEquals(userInfoDTO,msg.getData().getUser());
//        String token = msg.getData().getToken();
//        int userId = JWT.decode(token).getClaim("user_id").asInt();
//        int userType = JWT.decode(token).getClaim("user_type").asInt();
//        assertEquals(userId,100);
//        assertEquals(userType,0);

    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/users/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

//        先登录再登出
//        LoginDTO loginDTO = new LoginDTO("鲁迅", "123456");
//
//        mockMvc.perform(get("/users/login"))
//        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.LOGOUT_SUCCESS_MSG,msg.getMsg());
    }

    @Test
    public void testRegister() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO("admin1111", "mok", "123456", 0, "上海市闵行区", "mokkkkk@sjtu.edu.cn");
        User user = new User(100, "mok", "mokkkkk@sjtu.edu.cn", 0, "上海市闵行区", 100, "啥都不会", null);
        UserAuth userAuth = new UserAuth(100, "admin1111", "123456", 0, 0, 0, user, null);
        UserInfoDTO userInfoDTO = new UserInfoDTO(100, "mok", 0, "上海市闵行区", "这个人很懒，什么都没留下", null, 0, false);
        when(userService.findUserAuthByUsername("admin1111")).thenReturn(null).thenReturn(userAuth);
        when(userService.register(registerDTO)).thenReturn(userInfoDTO);

        String requestJson = JSONObject.toJSONString(registerDTO);
        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        //重复注册
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

    }

    @Test
    public void testFollow() throws Exception {
        when(userUtil.getUserId()).thenReturn(1);
        when(userService.follow(1, 2)).thenReturn(true);
//        MvcResult result =
        mockMvc.perform(post("/users/follow?follow_id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
//        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }

    @Test
    public void testUnFollow() throws Exception {

    }
}
