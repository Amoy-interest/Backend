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
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Map;
import java.util.AbstractMap;
@SpringBootTest
public class RedisServiceTest {
//    @InjectMocks
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Test
    public void testAll() {

        //全部incr
        redisService.incrementUserFollowCount(1);
        redisService.incrementUserFanCount(1);
        redisService.incrementUserBlogCount(1);
        redisService.incrementBlogCommentCount(1);
        redisService.incrementBlogReportCount(1);
        redisService.incrementUserReportCount(1);
        redisService.incrementBlogForwardCount(1);
        redisService.incrementVoteCount(1);

        //全部dec
        redisService.decrementUserFollowCount(1);
        redisService.decrementUserFanCount(1);
        redisService.decrementUserBlogCount(1);
        redisService.decrementBlogCommentCount(1);
        redisService.decrementBlogReportCount(1);
        redisService.decrementUserReportCount(1);
        redisService.decrementBlogForwardCount(1);
        redisService.decrementVoteCount(1);

        //全部set
        redisService.setVoteCount(1, 1);
        redisService.setBlogCommentCount(1, 1);
        redisService.setBlogForwardCount(1, 1);
        redisService.setUserFollowCount(1, 1);
        redisService.setUserFanCount(1, 1);
        redisService.setUserBlogCount(1, 1);

        //全部save
        redisService.saveVote2Redis(1, 1);
        redisService.saveBlogReport2Redis(1, 1, "软工太难顶了");
        redisService.saveUserReport2Redis(1, 1, "软工太难顶了");

        //全部get
        redisService.getBlogCommentCountFromRedis(1);
        redisService.getBlogCommentCountFromRedis();
        redisService.getBlogForwardCountFromRedis(1);
        redisService.getBlogForwardCountFromRedis();
        redisService.getBlogReportCountFromRedis();
        redisService.getBlogReportDataFromRedis();
        redisService.getVoteDataFromRedis();
        redisService.getVoteCountFromRedis(1);
        redisService.getVoteCountFromRedis();
        redisService.getUserBlogCountFromRedis();
        redisService.getUserBlogCountFromRedis(1);
        redisService.getUserFanCountFromRedis(1);
        redisService.getUserFanCountFromRedis();
        redisService.getUserFollowCountFromRedis();
        redisService.getUserFollowCountFromRedis(1);
        redisService.getUserReportCountFromRedis();
        redisService.getUserReportDataFromRedis();

        redisService.getBlogCommentCountFromRedis(10);
        redisService.getBlogForwardCountFromRedis(10);
        redisService.getVoteCountFromRedis(10);
        redisService.getUserBlogCountFromRedis(10);
        redisService.getUserFanCountFromRedis(10);
        redisService.getUserFollowCountFromRedis(10);

        //全部cancel
        redisService.cancelVoteFromRedis(1,1);

        //全部delete
        redisService.deleteVoteFromRedis(1, 1);
        List<Integer> list = new ArrayList<>(Collections.singletonList(1));
        redisService.deleteVoteCount(list);
        redisService.deleteBlogCommentCount(list);
        redisService.deleteBlogForwardCount(list);
        redisService.deleteUserBlogCount(list);
        redisService.deleteUserFollowCount(list);
        redisService.deleteUserFanCount(list);

        //其他
        redisService.findStatusFromRedis(1, 1);
        redisService.blogIsReported(1, 1);
        redisService.userIsReported(1, 1);

    }
}
