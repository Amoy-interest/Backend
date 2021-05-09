package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.dao.BlogCommentDao;
import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.dao.BlogImageDao;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import com.example.amoy_interest.serviceimpl.UserServiceImpl;
import com.example.amoy_interest.utils.CommonPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.amoy_interest.constant.Constant.TEST_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicControllerTest {
    private MockMvc mockMvc;

    private static TopicHeat topicHeat = new TopicHeat(1, 10);
    private static Topic topic = new Topic(1, "高考", new Date(), 0, 0, null, null, null, null, topicHeat);
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SecurityManager securityManager;

    @MockBean
    private TopicService topicService;
    @MockBean
    private BlogService blogService;

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
        JwtToken token = new JwtToken(TEST_TOKEN);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Test
    public void testGetTopicAll() throws Exception {
        TopicDTO topicDTO = new TopicDTO(1,"高考加油",new Date(),1,null,null,10);
        when(topicService.getTopicDTOByName("高考加油")).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/topics?topic_name=高考加油"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<TopicDTO> msg = om.readValue(resultContent, new TypeReference<Msg<TopicDTO>>() {
        });
        verify(topicService, times(1)).getTopicDTOByName("高考加油");
        assertEquals(-1, msg.getStatus());
        assertEquals("话题不存在", msg.getMsg());
        when(topicService.getTopicDTOByName("高考加油")).thenReturn(topicDTO);
        result = mockMvc.perform(get("/topics?topic_name=高考加油"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        resultContent = result.getResponse().getContentAsString();
        msg = om.readValue(resultContent, new TypeReference<Msg<TopicDTO>>() {
        });
        verify(topicService, times(2)).getTopicDTOByName("高考加油");
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testGetBlogs() throws Exception {
        Topic topic2 = new Topic(1, "高考", new Date(), 2, 0, null, null, null, null, topicHeat);
        when(topicService.getTopicByName(any())).thenReturn(null);
        mockMvc.perform(get("/topics/blogs?topic_name=你好")).andExpect(status().isOk());
        when(topicService.getTopicByName(any())).thenReturn(topic);
        mockMvc.perform(get("/topics/blogs?topic_name=你好")).andExpect(status().isOk());
        when(topicService.getTopicByName(any())).thenReturn(topic2);
        mockMvc.perform(get("/topics/blogs?topic_name=你好")).andExpect(status().isOk());
        when(topicService.getTopicByName(any())).thenReturn(topic);
        mockMvc.perform(get("/topics/blogs?topic_name=你好&orderType=1")).andExpect(status().isOk());
        verify(blogService, times(1)).getListByTopic_id(any(), any(), any());
        verify(blogService, times(1)).getHotBlogPageByTopic_id(any(), any(), any());
    }

    @Test
    public void testGetGroupBlogs() throws Exception {
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 5), 0);
        when(blogService.getBlogPageByGroupName(any(), any(), any())).thenReturn(page);
        mockMvc.perform(get("/topics/blogs/group?group_name=经济")).andExpect(status().isOk());
    }

    @Test
    public void testModifyTopicIntro() throws Exception {
        TopicModifyParam topicModifyParam = new TopicModifyParam("你好", "真不错", "123.jpg");
        String requestJson = JSONObject.toJSONString(topicModifyParam);
        when(topicService.modifyTopic(any())).thenReturn(null);
        mockMvc.perform(put("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTopic() throws Exception {
        when(topicService.addTopic(any())).thenReturn(null);
        mockMvc.perform(post("/topics?topic_name=高考")).andExpect(status().isOk());
    }

    @Test
    public void testGetHotList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<TopicHeatResult> topicHeatResultList = new ArrayList<>();
        topicHeatResultList.add(new TopicHeatResult("高考加油", 100));
        when(topicService.getHotList(0, 10)).thenReturn(new PageImpl<>(topicHeatResultList, pageable, 1));
        MvcResult result = mockMvc.perform(get("/topics/hotList"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg<CommonPage<TopicHeatResult>> msg = om.readValue(resultContent, new TypeReference<Msg<CommonPage<TopicHeatResult>>>() {
        });
        assertEquals(200, msg.getStatus());
        assertEquals(MsgUtil.SUCCESS_MSG, msg.getMsg());
    }

    @Test
    public void testReportTopic() throws Exception {
        when(topicService.reportTopicByName(any())).thenReturn(true);
        mockMvc.perform(post("/topics/report?topic_name=高考")).andExpect(status().isOk());
    }
}
