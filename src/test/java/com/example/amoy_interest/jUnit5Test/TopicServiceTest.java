package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.daoimpl.TopicDaoImpl;
import com.example.amoy_interest.daoimpl.TopicHeatDaoImpl;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicModifyParam;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TopicServiceTest {

    private static Topic topic = new Topic("测试", new Date(), 0, 0, 0, null, null);

    private static List<Topic> topicList = new ArrayList<>(Collections.singletonList(topic));

    private static TopicCheckDTO topicCheckDTO = new TopicCheckDTO("测试", 0);

    private static Pageable pageable = PageRequest.of(0, 1);

    private static Page<Topic> topicPage = new PageImpl<>(topicList, pageable, topicList.size());

    private static String str = "测试";

    private static List<String> strList = new ArrayList<>(Collections.singletonList(str));

    private static Page<String> strPage = new PageImpl<>(strList, pageable, strList.size());

    private static TopicModifyParam topicModifyParam = new TopicModifyParam("测试", "简介", "1.jpg");

    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicDaoImpl topicDao;

    @Mock
    private TopicHeatDaoImpl topicHeatDao;

    @Mock
    private RestHighLevelClient client;

    @Test
    public void testGetTopic_idByName(){
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.getTopic_idByName("测试");

        when(topicDao.getTopicByName(any())).thenReturn(null);
        topicService.getTopic_idByName("测试");
    }
    @Test
    public void testGetTopicDTOByName(){
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.getTopicDTOByName("测试");

        when(topicDao.getTopicByName(any())).thenReturn(null);
        topicService.getTopicDTOByName("测试");
    }
    @Test
    public void testGetTopicByName() {
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.getTopicByName("测试");
    }
    @Test
    public void testGetReportedTopics(){
        when(topicDao.getReportedTopic()).thenReturn(topicList);
        topicService.getReportedTopics();
    }
    @Test
    public void testCheckReportedTopic(){
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.checkReportedTopic(topicCheckDTO);
    }
    @Test
    public void testReportTopicByName(){
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.reportTopicByName("测试");
    }
    @Test
    public void testGetReportedTopicsPage(){
        when(topicDao.getReportedTopicPage(any())).thenReturn(topicPage);
        topicService.getReportedTopicsPage(0, 1, 0);
    }
    @Test
    public void testSearchReportedTopicsPage(){
        when(topicDao.searchReportedTopicPage(any(), any())).thenReturn(topicPage);
        topicService.searchReportedTopicsPage("测试", 0, 1, 0);
    }
    @Test
    public void testSearchTopicsPage(){
        when(topicDao.searchTopicsPage(any(), any())).thenReturn(strPage);
        topicService.searchTopicsPage("测试", 0, 1);
    }
    @Test
    public void testModifyTopic(){
        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.modifyTopic(topicModifyParam);

        when(topicDao.getTopicByName(any())).thenReturn(null);
        topicService.modifyTopic(topicModifyParam);
    }
    @Test
    public void testGetHotList(){

        TopicHeat topicHeat = new TopicHeat(1, 100);

        List<TopicHeat> heatList = new ArrayList<>();
        heatList.add(topicHeat);

        Page<TopicHeat> heatPage = new PageImpl<>(heatList, PageRequest.of(0, 5), 1);

        when(topicHeatDao.findByPage(any())).thenReturn(heatPage);

        Topic topic1 = new Topic("测试", new Date(), 0, 0, 0, null, null);
        when(topicDao.getTopicById(any())).thenReturn(topic1);
        topicService.getHotList(0, 1);
    }

    @Test
    public void testAddTopic() {
        when(topicDao.getTopicByName(any())).thenReturn(null);
        topicService.addTopic("测试");

        when(topicDao.getTopicByName(any())).thenReturn(topic);
        topicService.addTopic("测试");
    }
}
