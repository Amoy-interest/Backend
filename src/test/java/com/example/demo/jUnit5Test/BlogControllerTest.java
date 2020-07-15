package com.example.demo.jUnit5Test;

import com.example.demo.DemoApplicationTests;
import com.example.demo.controller.BlogController;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
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

import java.util.ArrayList;
import java.util.List;

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
        doNothing().when(blogService).deleteByBlog_id(Mockito.any());
        mockMvc.perform(delete("/blog?blog_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).deleteByBlog_id(1);
    }

    @Test
    public void testVote() throws Exception {
        doNothing().when(blogService).incrVoteCount(Mockito.any());
        doNothing().when(blogService).incrCommentVoteCount(Mockito.any());
        mockMvc.perform(post("/blog/vote?blog_id=1&comment_id=-1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrVoteCount(Mockito.any());
        mockMvc.perform(post("/blog/vote?blog_id=1&comment_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrCommentVoteCount(Mockito.any());
    }

    @Test
    public void testComment() throws Exception {
        Mockito.when(blogService.addBlogComment(Mockito.any())).thenReturn(null);
        mockMvc.perform(post("/blog/comment?blog_id=1&reply_comment_username=test&root_comment_id=1&text=%E6%98%A8%E6%99%9A%E5%8D%95%E6%89%93%E5%8F%88%E8%BE%93%E4%BA%86")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).addBlogComment(Mockito.any());
    }

    @Test
    public void testSearch() throws Exception {
        List<Blog> blogList = new ArrayList<>();
        blogList.add(new Blog(1, 1, 0, 0,null, "abbcdde",  false, 1, -1));
        blogList.add(new Blog(2, 1, 0, 0,null, "abcdde",  false, 1, -1));
        Mockito.when(blogService.getAllBlogs()).thenReturn(blogList);
        Mockito.when(blogService.getSimpleBlogDetail(1)).thenReturn(null);
        mockMvc.perform(get("/blog/search?keyword=abbc"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getAllBlogs();
        verify(blogService, times(1)).getSimpleBlogDetail(Mockito.any());
    }
}
