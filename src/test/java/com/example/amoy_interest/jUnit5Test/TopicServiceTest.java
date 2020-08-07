package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.daoimpl.TopicDaoImpl;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TopicServiceTest {
    @InjectMocks
    private TopicServiceImpl topicService;
    @Mock
    private TopicDaoImpl topicDao;
//
//    @Test
//    public void testGetTopicByName() throws Exception{
//        Date time = new Date();
//        Topic topic = new Topic(1,"高考加油",time,0,0,null,null,null,null);
//        when(topicDao.getTopicByName("高考加油")).thenReturn(topic);
//        TopicDTO topicDTO = topicService.getTopicDTOByName("高考加油");
//        assertEquals("高考加油",topicDTO.getName());
//        assertEquals(time,topicDTO.getTime());
//    }
//
//    @Test
//    public void testGetReportedTopics() throws Exception{
//        List<Topic> topicList = new ArrayList<>();
//        Date time = new Date();
//        for(int i = 1;i <= 10;i++) {
//            Topic topic = new Topic(1,"高考加油"+ Integer.toString(i),time,0,13,null,null,null,null);
//            topicList.add(topic);
//        }
//        when(topicDao.getReportedTopic()).thenReturn(topicList);
//        List<TopicReportDTO> topicReportDTOList = topicService.getReportedTopics();
//        assertEquals(10,topicReportDTOList.size());
//    }
//    @Test
//    public void CheckReportedTopic() throws Exception{
//        TopicCheckDTO topicCheckDTO = new TopicCheckDTO("高考加油",1);
//        Date time = new Date();
//        Topic topic = new Topic(1,"高考加油",time,0,13,null,null,null,null);
//        when(topicDao.getTopicByName("高考加油")).thenReturn(topic);
//        assertEquals(true,topicService.checkReportedTopic(topicCheckDTO));
//    }
}
