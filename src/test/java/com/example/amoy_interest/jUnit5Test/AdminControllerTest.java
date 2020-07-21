package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private UserService userService;
    @MockBean
    private BlogService blogService;
    @MockBean
    private TopicService topicService;

    private ObjectMapper om = new ObjectMapper();
    @Before
    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}

    @AfterEach
    void tearDown() {

    }

    @Test
    public void testBan() throws Exception{
        UserCheckDTO userCheckDTO = new UserCheckDTO(1, (long) 86400);
        String requestJson = JSONObject.toJSONString(userCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/users/ban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).ban(userCheckDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent,new TypeReference<Msg>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }
    @Test
    public void testUnban() throws Exception{
        Integer user_id = 1;
        String requestJson = JSONObject.toJSONString(user_id);
        MvcResult result = mockMvc.perform(put("/admins/users/unban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).unban(1);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent,new TypeReference<Msg>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }
    @Test
    public void testForbid() throws Exception{
        UserCheckDTO userCheckDTO = new UserCheckDTO(1, (long) 86400);
        String requestJson = JSONObject.toJSONString(userCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/users/forbid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).forbid(userCheckDTO);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent,new TypeReference<Msg>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }
    @Test
    public void testPermit() throws Exception{
        Integer user_id = 1;
        String requestJson = JSONObject.toJSONString(user_id);
        MvcResult result = mockMvc.perform(put("/admins/users/permit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(userService,times(1)).permit(1);
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent,new TypeReference<Msg>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }

    @Test
    public void testGetReportedBlogs() throws Exception{
        List<BlogCount> blogCounts = new ArrayList<>();
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        when(blogService.getAllReportedBlogs()).thenReturn(blogCounts);
        when(blogService.getSimpleBlogDetail(any())).thenReturn(null);
        mockMvc.perform(get("/admins/blogs/reported")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getAllReportedBlogs();
        verify(blogService, times(2)).getSimpleBlogDetail(Mockito.any());

    }
    @Test
    public void testCheckReportedBlog() throws Exception {
        when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        when(blogService.updateBlog(Mockito.any())).thenReturn(null);
        BlogCheckDTO blogCheckDTO = new BlogCheckDTO(1, 1);
        String requestJson = JSONObject.toJSONString(blogCheckDTO);
        mockMvc.perform(put("/admins/blogs/reported")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).findBlogByBlog_id(any());
        verify(blogService, times(1)).updateBlog(Mockito.any());
    }
    @Test
    public void testGetReportedUser() throws Exception{
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        for(int i = 1;i <= 10;i++) {
            UserReportDTO userReportDTO = new UserReportDTO(i,"mok"+Integer.toString(i),59);
            userReportDTOList.add(userReportDTO);
        }
        when(userService.getReportedUsers()).thenReturn(userReportDTOList);
        MvcResult result = mockMvc.perform(get("/admins/users/reported")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<List<UserReportDTO>> msg = om.readValue(resultContent,new TypeReference<Msg<List<UserReportDTO>>>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
        assertEquals(10,msg.getData().size());
    }
    @Test
    public void testGetReportedTopics() throws Exception{
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        Date time = new Date();
        for(int i = 1;i <= 10;i++) {
            TopicReportDTO topicReportDTO = new TopicReportDTO("高考加油"+Integer.toString(i),time,11);
            topicReportDTOList.add(topicReportDTO);
        }
        when(topicService.getReportedTopics()).thenReturn(topicReportDTOList);
        MvcResult result = mockMvc.perform(get("/admins/topics/reported")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<List<TopicReportDTO>> msg = om.readValue(resultContent,new TypeReference<Msg<List<TopicReportDTO>>>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
        assertEquals(10,msg.getData().size());
    }
    @Test
    public void testCheckReportedTopic() throws Exception{
        TopicCheckDTO topicCheckDTO = new TopicCheckDTO("高考加油",86400);
        when(topicService.CheckReportedTopic(topicCheckDTO)).thenReturn(true);
        String requestJson = JSONObject.toJSONString(topicCheckDTO);
        MvcResult result = mockMvc.perform(put("/admins/topics/reported")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent,new TypeReference<Msg>() {});
        assertEquals(0,msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
    }

}
