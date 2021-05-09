package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.daoimpl.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.*;
import com.example.amoy_interest.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class RecommendServiceTest {
    @InjectMocks
    private RecommendServiceImpl recommendService;

    @Mock
    private UserUtil userUtil;

    @Mock
    private RecommendBlogsDaoImpl recommendBlogsDao;

    @Mock
    private SimUserDaoImpl simUserDao;

    @Mock
    private SimBlogDaoImpl simBlogDao;

    @Mock
    private RedisServiceImpl redisService;

    @Mock
    private BlogVoteDaoImpl blogVoteDao;

    @Mock
    private BlogCountDaoImpl blogCountDao;

    @Mock
    private BlogServiceImpl blogService;

    @BeforeEach
    public void init() {

        //mock getBlogCount
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);

        //mock convertToBlogDTOList
        when(userUtil.getUserId()).thenReturn(1);
        when(redisService.findStatusFromRedis(1, 1)).thenReturn(1);
        BlogVote blogVote = new BlogVote(1, 1, 0);
        when(blogVoteDao.getByBlogIdAndUserId(1, 1)).thenReturn(blogVote); }

    @Test
    public void testGetRecommendBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog(1, 1, 0, new Date(), "test", false, 0, 0));
        Pageable pageable = PageRequest.of(0, 1);
        Page<Blog> page = new PageImpl<Blog>(blogs, pageable, blogs.size());
        when(recommendBlogsDao.getRecommendBlogsUsingUser_id(any(), any())).thenReturn(page);
        recommendService.takeRecommendBlogsUsingUser_id(1, 0, 5);

        Page<Blog> page1 = new PageImpl<Blog>(new ArrayList<>());
        when(recommendBlogsDao.getRecommendBlogsUsingUser_id(any(), any())).thenReturn(page1);
        recommendService.takeRecommendBlogsUsingUser_id(1, 0, 5);
    }

    @Test
    public void testGetSimBlogs() {
        Page<Blog> recBlogs = new PageImpl<>(new ArrayList<>());
        when(simBlogDao.getSimBlogUsingBlog_id(any(), any())).thenReturn(recBlogs);
        recommendService.getSimBlogUsingBlog_id(1, 1);
    }

    @Test
    public void testGetSimUsers() {
        when(simUserDao.getSimUserUsingUser_id(any(), any(), any())).thenReturn(null);
        recommendService.getSimUserUsingUser_id(1, 1, 1);
    }

}
