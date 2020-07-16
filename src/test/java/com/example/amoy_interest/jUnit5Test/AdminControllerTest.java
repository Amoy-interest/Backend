package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dto.BlogCheckDTO;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.service.UserService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void testBan() {

    }
    @Test
    public void testUnban() {

    }
    @Test
    public void testForbid() {

    }
    @Test
    public void testPermit() {

    }

    @Test
    public void testGetReportedBlogs() throws Exception{
        List<BlogCount> blogCounts = new ArrayList<>();
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        Mockito.when(blogService.getAllReportedBlogs()).thenReturn(blogCounts);
        Mockito.when(blogService.getSimpleBlogDetail(any())).thenReturn(null);
        mockMvc.perform(get("/admins/blogs/reported"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getAllReportedBlogs();
        verify(blogService, times(2)).getSimpleBlogDetail(Mockito.any());

    }
    @Test
    public void testCheckReportedBlog() throws Exception {
        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
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
}
