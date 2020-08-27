package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.controller.AdminController;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.CountService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import com.example.amoy_interest.serviceimpl.UserServiceImpl;
import com.example.amoy_interest.utils.CommonPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdminControllerTest {
    private MockMvc mockMvc;

    private final static Integer pageNum1 = 1;
    private final static Integer pageSize1 = 5;
    private final static Integer orderType1 = 1;
    private final static String token1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMSIsImN1cnJlbnRUaW1lTWlsbGlzIjoiMTU5ODQwODAyMTU0OSIsImV4cCI6MTkxMzc2ODAyMSwidXNlcm5hbWUiOiLpsoHov4UifQ.FSxvme-or5PLR23LYNfgcD4k6P7p_uqVbYegdJVA3HE";
    //    @InjectMocks
//    private AdminController adminController;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SecurityManager securityManager;
    @MockBean
    private UserService userService;
    @MockBean
    private BlogService blogService;
    @MockBean
    private TopicService topicService;
    @MockBean
    private CountService countService;

    private ObjectMapper om = new ObjectMapper();

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
        JwtToken token = new JwtToken(token1);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Test
    public void testGetReportedBlogs() throws Exception {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        blogDTOList.add(new BlogDTO());
        Pageable pageable = PageRequest.of(pageNum1, pageSize1);
        Page<BlogDTO> blogDTOPage = new PageImpl<>(blogDTOList, pageable, 0);
        when(blogService.getReportedBlogsPage(pageNum1, pageSize1, orderType1)).thenReturn(blogDTOPage);
        mockMvc.perform(get("/admins/blogs/reported/?orderType=1&pageNum=1&pageSize=5"))
//                .header("Authorization", token1))
                .andExpect(status().isOk())
                .andReturn();
        verify(blogService, times(1)).getReportedBlogsPage(any(), any(), any());
    }

    @Test
    public void testSearchReportedBlogs() throws Exception {
        Pageable pageable = PageRequest.of(pageNum1, pageSize1);
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(), pageable, 0);
        when(blogService.searchReportedBlogsPage(any(), any(), any(), any())).thenReturn(page);
        MvcResult result = mockMvc.perform(get("/admins/blogs/reported/search?keyword=你好吗&orderType=1&pageNum=1&pageSize=5"))
                .andExpect(status().isOk())
                .andReturn();
        verify(blogService, times(1)).searchReportedBlogsPage("你好吗", 1, 5, 1);
    }

    @Test
    public void testCheckReportedBlog() throws Exception {
        when(blogService.checkReportedBlog(any())).thenReturn(true);
        BlogCheckDTO blogCheckDTO = new BlogCheckDTO(1, 1);
        String requestJson = JSONObject.toJSONString(blogCheckDTO);
        mockMvc.perform(put("/admins/blogs/reported")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).checkReportedBlog(any());
//        verify(blogService, times(1)).updateBlog(Mockito.any());
    }

    @Test
    public void testGetReportedTopics() throws Exception {
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        Date time = new Date();
        for (int i = 1; i <= 10; i++) {
            TopicReportDTO topicReportDTO = new TopicReportDTO("高考加油" + Integer.toString(i), time, 11);
            topicReportDTOList.add(topicReportDTO);
        }
        Pageable pageable = PageRequest.of(pageNum1, pageSize1);
        Page<TopicReportDTO> page = new PageImpl<>(topicReportDTOList, pageable, 10);
        when(topicService.getReportedTopicsPage(pageNum1, pageSize1, orderType1)).thenReturn(page);
        MvcResult result = mockMvc.perform(get("/admins/topics/reported/?orderType=1&pageNum=1&pageSize=5"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<CommonPage<TopicReportDTO>> msg = om.readValue(resultContent, new TypeReference<Msg<CommonPage<TopicReportDTO>>>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
//        assertEquals(10,msg.getData());
    }

    @Test
    public void testSearchReportedTopics() throws Exception {
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNum1, pageSize1);
        Page<TopicReportDTO> page = new PageImpl<>(topicReportDTOList, pageable, 10);
        when(topicService.searchReportedTopicsPage(any(), any(), any(), any())).thenReturn(page);
        MvcResult result = mockMvc.perform(get("/admins/topics/reported/search/?keyword=中国&orderType=1&pageNum=1&pageSize=5"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<CommonPage<TopicReportDTO>> msg = om.readValue(resultContent, new TypeReference<Msg<CommonPage<TopicReportDTO>>>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testCheckReportedTopic() throws Exception {
        TopicCheckDTO topicCheckDTO = new TopicCheckDTO("高考加油", 1);
        when(topicService.checkReportedTopic(any())).thenReturn(true);
        String requestJson = JSONObject.toJSONString(topicCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/topics/reported")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(topicService,times(1)).checkReportedTopic(topicCheckDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(200, msg.getStatus());
    }

    @Test
    public void testGetReportedUser() throws Exception {
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNum1,pageSize1);
        Page<UserReportDTO> page = new PageImpl<>(userReportDTOList,pageable,0);
        MvcResult result = mockMvc.perform(get("/admins/users/reported"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<CommonPage<UserReportDTO>> msg = om.readValue(resultContent, new TypeReference<Msg<CommonPage<UserReportDTO>>>() {
        });
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
//        assertEquals(10,msg.getData().size());
    }

    @Test
    public void testSearchReportedUser() throws Exception {
        mockMvc.perform(get("/admins/users/reported/search?keyword=你好")).andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).searchReportedUsersPage("你好",0,5,1);
    }

    @Test
    public void testBan() throws Exception {
        UserCheckDTO userCheckDTO = new UserCheckDTO(1, (long) 86400);
        String requestJson = JSONObject.toJSONString(userCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/users/ban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(userService, times(1)).ban(userCheckDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testUnban() throws Exception {
        Integer user_id = 1;
        String requestJson = JSONObject.toJSONString(user_id);
        MvcResult result = mockMvc.perform(put("/admins/users/unban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(userService, times(1)).unban(1);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testForbid() throws Exception {
        UserCheckDTO userCheckDTO = new UserCheckDTO(1, (long) 86400);
        String requestJson = JSONObject.toJSONString(userCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/users/forbid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
//        verify(userService,times(1)).forbid(userCheckDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testPermit() throws Exception {
        Integer user_id = 1;
        String requestJson = JSONObject.toJSONString(user_id);
        MvcResult result = mockMvc.perform(put("/admins/users/permit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
//        verify(userService,times(1)).permit(1);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testGetBan() throws Exception {
        mockMvc.perform(get("/admins/users/ban")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetForbid() throws Exception {
        mockMvc.perform(get("/admins/users/ban")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testSearchBan() throws Exception {
        mockMvc.perform(get("/admins/users/ban/search?keyword=你好&pageNum=0&pageSize=5")).andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).searchUserBanPage("你好",0,5);
    }

    @Test
    public void testSearchForbid() throws Exception {
        mockMvc.perform(get("/admins/users/forbid/search?keyword=你好")).andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).searchUserForbidPage("你好",0,5);
    }

    @Test
    public void testUpdateReport() throws Exception {
        mockMvc.perform(get("/admins/report/update")).andExpect(status().isOk()).andReturn();
        verify(countService,times(1)).transUserReporterDataFromRedis2DB();
        verify(countService,times(1)).transBlogReportDataFromRedis2DB();
    }
}
