package com.example.demo.jUnit5Test;

import com.auth0.jwt.JWT;
import com.example.demo.controller.UserController;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;
import com.example.demo.entity.UserCount;
import com.example.demo.msgutils.Msg;
import com.example.demo.msgutils.MsgCode;
import com.example.demo.msgutils.MsgUtil;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserController userController;

    private ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}

    @AfterEach
    void tearDown() {

    }
    @Test
    public void testLogin() throws Exception{
        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"啥都不会",null);
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,user);
        UserInfoDTO userInfoDTO = new UserInfoDTO("mok",0,"上海市闵行区","啥都不会",null);
        when(userService.findUserAuthByUsername("admin")).thenReturn(userAuth);
        LoginDTO loginDTO = new LoginDTO("admin","123456");

        String requestJson = JSONObject.toJSONString(loginDTO);
        MvcResult result = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.LOGIN_SUCCESS_MSG,msg.getMsg());
        assertEquals(userInfoDTO,msg.getData().getUser());
        String token = msg.getData().getToken();
        int userId = JWT.decode(token).getClaim("user_id").asInt();
        int userType = JWT.decode(token).getClaim("user_type").asInt();
        assertEquals(userId,100);
        assertEquals(userType,0);

        //测试时间？
    }
    @Test
    public void testRegister() throws Exception {

        RegisterDTO registerDTO = new RegisterDTO("admin","mok","123456",0,"上海市闵行区","mokkkkk@sjtu.edu.cn");
        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"啥都不会",null);
        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,user);
        UserInfoDTO userInfoDTO = new UserInfoDTO("mok",0,"上海市闵行区","这个人很懒，什么都没留下",null);
        when(userService.findUserAuthByUsername("admin")).thenReturn(null).thenReturn(userAuth);
        when(userService.register(registerDTO)).thenReturn(userInfoDTO);
//        String example = "{"username":"admin"}";

        String requestJson = JSONObject.toJSONString(registerDTO);
        MvcResult result = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
//        System.out.println(userInfoDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<UserDTO> msg = om.readValue(resultContent,new TypeReference<Msg<UserDTO>>() {});
        verify(userService,times(2)).findUserAuthByUsername("admin");
        verify(userService,times(1)).register(registerDTO);
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.REGISTER_SUCCESS_MSG,msg.getMsg());
        assertEquals(userInfoDTO,msg.getData().getUser());
        String token = msg.getData().getToken();
        int userId = JWT.decode(token).getClaim("user_id").asInt();
        int userType = JWT.decode(token).getClaim("user_type").asInt();
        assertEquals(userId,100);
        assertEquals(userType,0);
    }
}
