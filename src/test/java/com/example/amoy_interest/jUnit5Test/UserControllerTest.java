package com.example.amoy_interest.jUnit5Test;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

//    @InjectMocks
//    private UserController userController;

    private ObjectMapper om = new ObjectMapper();
//
//    @BeforeEach
//    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}
//
//    @Test
//    public void testLogin() throws Exception{
//        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"啥都不会",null);
//        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0,user,null);
//        user.setUserAuth(userAuth);
//        UserInfoDTO userInfoDTO = new UserInfoDTO(100,"mok",0,"上海市闵行区","啥都不会",null,0,false);
//        when(userService.findUserAuthByUsername("admin")).thenReturn(userAuth);
//        LoginDTO loginDTO = new LoginDTO("admin","123456");
//
//        String requestJson = JSONObject.toJSONString(loginDTO);
//        MvcResult result = mockMvc.perform(post("/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson))
//                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
////        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
////        assertEquals(0,msg.getStatus());
////        assertEquals(MsgUtil.LOGIN_SUCCESS_MSG,msg.getMsg());
////        assertEquals(userInfoDTO,msg.getData().getUser());
////        String token = msg.getData().getToken();
////        int userId = JWT.decode(token).getClaim("user_id").asInt();
////        int userType = JWT.decode(token).getClaim("user_type").asInt();
////        assertEquals(userId,100);
////        assertEquals(userType,0);
//
//        //测试时间？
//    }
//    @Test
//    public void testRegister() throws Exception {
//
//        RegisterDTO registerDTO = new RegisterDTO("admin1111","mok","123456",0,"上海市闵行区","mokkkkk@sjtu.edu.cn");
//        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"啥都不会",null);
//        UserAuth userAuth = new UserAuth(100,"admin1111","123456",0,0,0,user,null);
//        UserInfoDTO userInfoDTO = new UserInfoDTO(100,"mok",0,"上海市闵行区","这个人很懒，什么都没留下",null,0,false);
//        when(userService.findUserAuthByUsername("admin1111")).thenReturn(null).thenReturn(userAuth);
//        when(userService.register(registerDTO)).thenReturn(userInfoDTO);
//
//        String requestJson = JSONObject.toJSONString(registerDTO);
//        MvcResult result = mockMvc.perform(post("/users/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson))
//                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
////        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
//////        verify(userService,times(2)).findUserAuthByUsername("admin");
//////        verify(userService,times(1)).register(registerDTO);
//////        assertEquals(0,msg.getStatus());
////        assertEquals(MsgUtil.REGISTER_SUCCESS_MSG,msg.getMsg());
////        assertEquals(userInfoDTO,msg.getData().getUser());
////        String token = msg.getData().getToken();
////        int userId = JWT.decode(token).getClaim("user_id").asInt();
////        int userType = JWT.decode(token).getClaim("user_type").asInt();
////        assertEquals(userId,100);
////        assertEquals(userType,0);
//    }
//    @Test
//    public void testLogout() throws Exception{
//        MvcResult result = mockMvc.perform(get("/users/logout")
//                .header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjEsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NzMwNDM0fQ.WutDjmVIq5i2obrVmf-_pA_0jcTPtY7zUTJC-Oc40E4")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
////        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
////        assertEquals(0,msg.getStatus());
////        assertEquals(MsgUtil.LOGOUT_SUCCESS_MSG,msg.getMsg());
//    }
//    @Test
//    public void testFollow() throws Exception{
//        when(userService.follow(1,2)).thenReturn(true);
//        MvcResult result = mockMvc.perform(post("/users/follow?follow_id=2")
//                .header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjEsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NzMwNDM0fQ.WutDjmVIq5i2obrVmf-_pA_0jcTPtY7zUTJC-Oc40E4")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
////        verify(userService,times(1)).follow(1,2);
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
////        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
////        assertEquals(0,msg.getStatus());
////        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
//    }
}
