package com.example.demo.jUnit5Test;

import com.example.demo.DemoApplicationTests;
import com.example.demo.controller.BlogController;
import com.example.demo.entity.Blog;
import com.example.demo.serviceimpl.BlogServiceImpl;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogControllerTest extends DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @Mock
    private BlogServiceImpl blogService;

    @InjectMocks
    private BlogController blogController;

    @Test
    public void testGetBlog() throws Exception {
        Mockito.when(blogService.getAllBlogDetail(1)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/blog?blog_id=1").header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
    }
    //直接null可以吗。。。又没对返回的东西做加工啥的。。

    @Test
    public void testAddBlog() throws Exception {
        Mockito.when(blogService.addBlog(Mockito.any())).thenReturn(null);
        mockMvc.perform(post("/blog?images=test.jpg&text=%E5%BF%AB%E8%AE%A9%E6%88%91%E4%B8%AD%E4%B8%AA%E5%BD%A9%E7%A5%A8%E5%90%A7")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).addBlog(Mockito.any());
    }

    @Test
    public void testUpdateBlog() throws Exception {
        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
        mockMvc.perform(put("/blog?blog_id=1&text=%E8%BF%99%E4%B8%AA%E5%BC%9F%E5%BC%9F")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).updateBlog(Mockito.any());
    }

    @Test
    public void testDeleteBlog() throws Exception {
        mockMvc.perform(delete("/blog?blog_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).deleteByBlog_id(1);
    }

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post("/blog/vote?blog_id=1&comment_id=-1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrVoteCount(Mockito.any());
        verify(blogService, times(0)).incrCommentVoteCount(Mockito.any());
        mockMvc.perform(post("/blog/vote?blog_id=1&comment_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();

    }

}
