package com.example.amoy_interest.jUnit5Test;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceTest {
    @Autowired
    private TopicService topicService;
    @MockBean
    private TopicDao topicDao;

    @Test
    public void testGetTopicByName() {
        List<Blog> blogList = new ArrayList<>();
        Date time = new Date();
        for(int i = 1;i <= 10;i++) {
            Blog blog = new Blog(i,1,1,0,time,"test",false,0,-1);
            User user = new User(1,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
            BlogCount blogCount = new BlogCount(i,0,0,0,0);
            blog.setBlogCount(blogCount);
            blog.setBlogImages(null);
            blog.setUser(user);
            blogList.add(blog);
        }
        for(int i = 11;i <= 18;i++) {
            Blog blog = new Blog(i,1,1,0,time,"test",true,0,-1);
            User user = new User(1,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
//            BlogImage blogImage = new BlogImage(i,null);
            BlogCount blogCount = new BlogCount(i,0,0,0,0);
            blog.setBlogCount(blogCount);
            blog.setUser(user);
            blogList.add(blog);
        }
        for(int i = 19;i <= 25;i++) {
            Blog blog = new Blog(i,1,1,0,time,"test",false,2,-1);
            User user = new User(1,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
//            BlogImage blogImage = new BlogImage(i,null);
            BlogCount blogCount = new BlogCount(i,0,0,0,0);
            blog.setBlogCount(blogCount);
            blog.setUser(user);
            blogList.add(blog);
        }
        for(int i = 26;i <= 27;i++) {
            Blog blog = new Blog(i,1,1,0,time,"test",false,1,-1);
            User user = new User(1,"mok","mokkkkk@sjtu.edu.cn",0,"上海市闵行区",100,"这个人很懒，什么都没留下",null);
//            BlogImage blogImage = new BlogImage(i,null);
            BlogCount blogCount = new BlogCount(i,0,0,0,0);
            blog.setBlogCount(blogCount);
            blog.setUser(user);
            blogList.add(blog);
        }
//        Topic topic = new Topic(1,"高考加油",time,0,0);
//        topic.setBlogs(blogList);
//        when(topicDao.getTopicByName("高考加油")).thenReturn(topic);
        TopicDTO topicDTO = topicService.getTopicDTOByName("高考加油");
//        assertEquals(12, topicDTO.getBlogs().size());
        assertEquals("高考加油",topicDTO.getName());
        assertEquals(time,topicDTO.getTime());
    }

    @Test
    public void testGetReportedTopics() throws Exception{
        List<Topic> topicList = new ArrayList<>();
        Date time = new Date();
        for(int i = 1;i <= 10;i++) {
//            Topic topic = new Topic(1,"高考加油"+ Integer.toString(i),time,0,13);
//            topicList.add(topic);
        }
        when(topicDao.getReportedTopic()).thenReturn(topicList);
        List<TopicReportDTO> topicReportDTOList = topicService.getReportedTopics();
        assertEquals(10,topicReportDTOList.size());
    }
    @Test
    public void CheckReportedTopic() throws Exception{
        TopicCheckDTO topicCheckDTO = new TopicCheckDTO("高考加油",1);
        Date time = new Date();
//        Topic topic = new Topic(1,"高考加油",time,0,13);
//        when(topicDao.getTopicByName("高考加油")).thenReturn(topic);
        assertEquals(true,topicService.checkReportedTopic(topicCheckDTO));
    }
}
