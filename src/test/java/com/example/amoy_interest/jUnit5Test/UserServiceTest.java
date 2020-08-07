package com.example.amoy_interest.jUnit5Test;
import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.daoimpl.UserAuthDaoImpl;
import com.example.amoy_interest.daoimpl.UserCountDaoImpl;
import com.example.amoy_interest.daoimpl.UserDaoImpl;
import com.example.amoy_interest.daoimpl.UserFollowDaoImpl;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import com.example.amoy_interest.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserAuthDaoImpl userAuthDao;
    @Mock
    private UserCountDaoImpl userCountDao;
    @Mock
    private UserDaoImpl userDao;
    @Mock
    private UserFollowDaoImpl userFollowDao;
//    @Test
//    public void TestRegister() {
//        RegisterDTO registerDTO = new RegisterDTO("admin","mok","123456",0,"上海市闵行区","mokkkkk@sjtu.edu.cn");
//        UserAuth insert_userAuth = new UserAuth("admin","123456",0,0,0);
//        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
//        User user = new User(100,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
//        user.setUserAuth(userAuth);
//        UserCount userCount = new UserCount(100,0,0,0);
//        UserInfoDTO userInfoDTO = new UserInfoDTO(100,"mok",0,"上海市闵行区","这个人很懒，什么都没留下",null,0,false);
//        when(userAuthDao.insert(insert_userAuth)).thenReturn(userAuth);
//
//        assertEquals(userInfoDTO,userService.register(registerDTO));
//        verify(userAuthDao,times(1)).insert(insert_userAuth);
//        verify(userCountDao,times(1)).insert(userCount);
//        verify(userDao,times(1)).insert(user);
//    }
//    @Test
//    public void TestFollow() {
//        UserFollow userFollow = new UserFollow(2,4);
//        userService.follow(2,4);
//        verify(userFollowDao,times(1)).insert(userFollow);
//    }
//    @Test
//    public void testfindUserAuthById() {
//        Integer id = 100;
//        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
//        when(userAuthDao.findUserById(id)).thenReturn(userAuth);
//        assertEquals(userAuth,userService.findUserAuthById(id));
//        verify(userAuthDao,times(1)).findUserById(id);
//    }
//
//    @Test
//    public void testfindUserAuthByUsername() {
//        String username = "admin";
//        UserAuth userAuth = new UserAuth(100,"admin","123456",0,0,0);
//        when(userAuthDao.findUserByUsername(username)).thenReturn(userAuth);
//        assertEquals(userAuth,userService.findUserAuthByUsername(username));
//        verify(userAuthDao,times(1)).findUserByUsername(username);
//    }
//
}
