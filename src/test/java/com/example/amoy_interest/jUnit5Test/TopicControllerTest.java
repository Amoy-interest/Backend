package com.example.amoy_interest.jUnit5Test;
import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.BlogCommentDao;
import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.dao.BlogImageDao;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.entity.BlogImage;
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
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TopicService topicService;

    private ObjectMapper om = new ObjectMapper();
//    @BeforeEach
//    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(context).build();}
//
//    @Test
//    public void testGetTopic() throws Exception{
//        when(topicService.getTopicDTOByName("高考加油")).thenReturn(null);
//        MvcResult result = mockMvc.perform(get("/topics?topic_name=高考加油"))
//                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
//        Msg<TopicDTO> msg = om.readValue(resultContent,new TypeReference<Msg<TopicDTO>>() {});
//        verify(topicService,times(1)).getTopicDTOByName("高考加油");
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
//    }
//    @Test
//    public void testGetHotList() throws Exception{
//        Pageable pageable = PageRequest.of(0,10);
//        List<TopicHeatResult> topicHeatResultList = new ArrayList<>();
//        topicHeatResultList.add(new TopicHeatResult("高考加油",100));
//        when(topicService.getHotList(0,10)).thenReturn(new PageImpl<>(topicHeatResultList,pageable,1));
//        MvcResult result = mockMvc.perform(get("/topics/hotList"))
//                .andExpect(status().isOk()).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8"); //解决中文乱码
//        String resultContent = result.getResponse().getContentAsString();
//        Msg<CommonPage<TopicHeatResult>> msg = om.readValue(resultContent,new TypeReference<Msg<CommonPage<TopicHeatResult>>>() {});
//        assertEquals(0,msg.getStatus());
//        assertEquals(MsgUtil.SUCCESS_MSG,msg.getMsg());
//    }
}
