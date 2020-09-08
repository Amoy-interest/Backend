package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import com.example.amoy_interest.serviceimpl.UserServiceImpl;
import com.example.amoy_interest.utils.CommonPage;
import com.example.amoy_interest.utils.UserUtil;
import com.example.amoy_interest.utils.sensitivefilter2.Finder;
import com.example.amoy_interest.utils.sensitivefilter2.FinderUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.*;

import static com.example.amoy_interest.constant.Constant.TEST_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

    private MockMvc mockMvc;

    private static UserAuth testAuth = new UserAuth(1, "鲁迅", "123456", 1, 0, 0);
    private static UserAuth testAuth2 = new UserAuth(1, "鲁迅", "123456", 1, 1, 1);
    private static User testUser = new User(1, "鲁迅", "123@163.com", 0, "广东", 0, "无", null);
    private static Blog testBlog = new Blog(1, 1, 0, new Date(), "今天天气好", false, 0, 0);
    private static BlogCount testBlogCount = new BlogCount(1, 0, 0, 0, 0);
    private static BlogDTO testBlogDTO = null;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SecurityManager securityManager;

    private ObjectMapper om = new ObjectMapper();

    //
    @BeforeAll
    public static void init() {
        testBlog.setUser(testUser);
        testBlog.setTopics(new ArrayList<>());
        testBlogDTO = new BlogDTO(testBlog, testBlogCount, false);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime();
        testAuth2.setUserBan(new UserBan(1, date, date));
    }

    @MockBean
    private UserUtil userUtil;
    @MockBean
    private BlogService blogService;
    @MockBean
    private RecommendService recommendService;
    @BeforeEach
    public void setup() {
        Finder.clearSensitiveWords();
        Finder.addSensitiveWords("去他的");
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
    public void testAddBlog() throws Exception {
        Mockito.when(userUtil.getUser()).thenReturn(testAuth);
        Mockito.when(blogService.addBlog(Mockito.any())).thenReturn(null);
        Set<String> set = new LinkedHashSet<String>();
        BlogAddDTO blogAddDTO = new BlogAddDTO("你好", null, null, 1);
        String requestJson = JSONObject.toJSONString(blogAddDTO);
        mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
        verify(blogService, times(1)).addBlog(Mockito.any());

        Mockito.when(userUtil.getUser()).thenReturn(testAuth2);
        MvcResult result = mockMvc.perform(post("/blogs").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(402, msg.getStatus());
        assertEquals(MsgUtil.USER_BAN_MSG, msg.getMsg());
        Mockito.when(userUtil.getUser()).thenReturn(testAuth);
        blogAddDTO.setText("去他的");
        requestJson = JSONObject.toJSONString(blogAddDTO);
        MvcResult result2 = mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        result2.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent2 = result2.getResponse().getContentAsString();
        Msg msg2 = om.readValue(resultContent2, new TypeReference<Msg>() {
        });
        assertEquals(402, msg2.getStatus());
        assertEquals("内容含有敏感词", msg2.getMsg());
    }

    @Test
    public void testGetBlog() throws Exception {
        Mockito.when(blogService.getAllBlogDetail(1)).thenReturn(testBlogDTO);
        mockMvc.perform(get("/blogs?blog_id=1"))//.header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService,times(1)).getAllBlogDetail(1);
    }

    @Test
    public void testPutBlog() throws Exception {
        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(testBlog);
        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
        Mockito.when(userUtil.getUser()).thenReturn(testAuth2);
        BlogPutDTO blogPutDTO = new BlogPutDTO(1, "dest", null);
        String requestJson = JSONObject.toJSONString(blogPutDTO);
        MvcResult result = mockMvc.perform(put("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(402, msg.getStatus());
        assertEquals(MsgUtil.USER_BAN_MSG, msg.getMsg());

        Mockito.when(userUtil.getUser()).thenReturn(testAuth);
        mockMvc.perform(put("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();


        blogPutDTO.setText("去他的");
        requestJson = JSONObject.toJSONString(blogPutDTO);
        result = mockMvc.perform(put("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        resultContent = result.getResponse().getContentAsString();
        msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(402, msg.getStatus());
        assertEquals("内容含有敏感词", msg.getMsg());

        verify(blogService, times(1)).updateBlog(Mockito.any());
    }

    @Test
    public void testForwardBlog() throws Exception {
        Mockito.when(userUtil.getUser()).thenReturn(testAuth2);
        BlogForwardDTO blogForwardDTO = new BlogForwardDTO(1,"DEST",1);
        String requestJson = JSONObject.toJSONString(blogForwardDTO);
        MvcResult result = mockMvc.perform(post("/blogs/forward")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码

        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(402, msg.getStatus());
        assertEquals(MsgUtil.USER_BAN_MSG, msg.getMsg());

        Mockito.when(userUtil.getUser()).thenReturn(testAuth);
        mockMvc.perform(post("/blogs/forward")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();


        blogForwardDTO.setText("去他的");
        requestJson = JSONObject.toJSONString(blogForwardDTO);
        result = mockMvc.perform(post("/blogs/forward")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        resultContent = result.getResponse().getContentAsString();
        msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(402, msg.getStatus());
        assertEquals("内容含有敏感词", msg.getMsg());
        verify(blogService, times(1)).forwardBlog(Mockito.any());
    }

    @Test
    public void testDeleteBlog() throws Exception {
        when(blogService.deleteByBlog_id(Mockito.any())).thenReturn(0);
        MvcResult result = mockMvc.perform(delete("/blogs?blog_id=1"))
                .andExpect(status().isOk()).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
        String resultContent = result.getResponse().getContentAsString();
        Msg msg = om.readValue(resultContent, new TypeReference<Msg>() {
        });
        assertEquals(-1, msg.getStatus());
        assertEquals("你无权删除", msg.getMsg());
        when(blogService.deleteByBlog_id(Mockito.any())).thenReturn(1);
        mockMvc.perform(delete("/blogs?blog_id=1"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(2)).deleteByBlog_id(1);
    }

    @Test
    public void testComment() throws Exception {
        when(userUtil.getUserId()).thenReturn(1);
        when(blogService.addBlogComment(Mockito.any())).thenReturn(null);
        CommentPostDTO commentPostDTO = new CommentPostDTO(1, 1, 1, 1, "test");
        String requestJson = JSONObject.toJSONString(commentPostDTO);
        mockMvc.perform(post("/blogs/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).addBlogComment(Mockito.any());
    }


    @Test
    public void testDeleteComment() throws Exception {
        when(blogService.deleteCommentByComment_id(any())).thenReturn(true);
        mockMvc.perform(delete("/blogs/comments?comment_id=1"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).deleteCommentByComment_id(any());
    }

    @Test
    public void testGetLevel1Comments() throws Exception {
        when(blogService.getLevel1CommentPage(1,0,5)).thenReturn(new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0));
        mockMvc.perform(get("/blogs/comments/level1?blog_id=1")).andExpect(status().isOk());
    }

    @Test
    public void testGetMultiComments() throws Exception {
        when(blogService.getMultiLevelCommentPage(1,0,5)).thenReturn(new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0));
        mockMvc.perform(get("/blogs/comments/multilevel?root_comment_id=1")).andExpect(status().isOk());
    }

    @Test
    public void testVote() throws Exception {
        doNothing().when(blogService).incrVoteCount(Mockito.any());
        doNothing().when(blogService).incrCommentVoteCount(Mockito.any());
        VoteDTO voteDTO = new VoteDTO(1, 0);
        String requestJson = JSONObject.toJSONString(voteDTO);
        mockMvc.perform(post("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrVoteCount(Mockito.any());
        VoteDTO voteDTO1 = new VoteDTO(2, 1);
        String requestJson1 = JSONObject.toJSONString(voteDTO1);
        mockMvc.perform(post("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson1))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrCommentVoteCount(Mockito.any());
    }

    @Test
    public void testCancelVote() throws Exception {
        doNothing().when(blogService).decrVoteCount(Mockito.any());
        doNothing().when(blogService).decrCommentVoteCount(Mockito.any());
        VoteDTO voteDTO = new VoteDTO(1, 0);
        String requestJson = JSONObject.toJSONString(voteDTO);
        mockMvc.perform(delete("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).decrVoteCount(Mockito.any());
        VoteDTO voteDTO1 = new VoteDTO(2, 1);
        String requestJson1 = JSONObject.toJSONString(voteDTO1);
        mockMvc.perform(delete("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson1))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).decrCommentVoteCount(Mockito.any());
    }


    @Test
    public void testSearch() throws Exception {
        List<BlogDTO> blogList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<BlogDTO> page = new PageImpl<>(blogList, pageable, 0);
        Mockito.when(blogService.getSearchListByBlog_text("abbc", 0, 5)).thenReturn(page);
        mockMvc.perform(get("/blogs/search?keyword=abbc"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getSearchListByBlog_text("abbc", 0, 5);
    }

    @Test
    public void testGetSimBlog() throws Exception{
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0);
        when(recommendService.getSimBlogUsingBlog_id(any(),any())).thenReturn(page);
        mockMvc.perform(get("/blogs/sim?blog_id=1")).andExpect(status().isOk());
    }
    @Test
    public void testGetRecommendBlog() throws Exception {
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0);
        when(userUtil.getUserId()).thenReturn(1);
        when(recommendService.takeRecommendBlogsUsingUser_id(any(),any(),any())).thenReturn(page);
        mockMvc.perform(get("/blogs/recommend")).andExpect(status().isOk());
    }

    @Test
    public void testGetFollowBlogs() throws Exception {
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0);
        when(userUtil.getUserId()).thenReturn(1);
        when(blogService.getFollowBlogPageByUser_idOrderByTime(any(),any(),any())).thenReturn(page);
        mockMvc.perform(get("/blogs/follow")).andExpect(status().isOk());
    }

    @Test
    public void testGetUserBlogs() throws Exception {
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0);
        when(blogService.getBlogPageByUser_idOrderByTime(any(),any(),any())).thenReturn(page);
        when(blogService.getHotBlogPageByUser_id(any(),any(),any())).thenReturn(page);
        mockMvc.perform(get("/blogs/users?user_id=1&orderType=0")).andExpect(status().isOk());
        mockMvc.perform(get("/blogs/users?user_id=1&orderType=1")).andExpect(status().isOk());
//        verify(blogService.getBlogPageByUser_idOrderByTime(any(),any(),any()),times(1));
//        verify(blogService.getHotBlogPageByUser_id(any(),any(),any()),times(1));
    }

    @Test
    public void testGetBeforeLoginBlogs() throws Exception {
        Page<BlogDTO> page = new PageImpl<>(new ArrayList<>(),PageRequest.of(0,5),0);
        when(blogService.getBlogPageOrderByHot(any(),any())).thenReturn(page);
        mockMvc.perform(get("/blogs/beforeLogin")).andExpect(status().isOk());
    }

    @Test
    public void testReportBlog() throws Exception {
        BlogReportDTO blogReportDTO = new BlogReportDTO(1,"bad");
        when(blogService.reportBlog(any())).thenReturn(true);
        String requestJson = JSONObject.toJSONString(blogReportDTO);
        mockMvc.perform(post("/blogs/report").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andExpect(status().isOk());
    }
}
