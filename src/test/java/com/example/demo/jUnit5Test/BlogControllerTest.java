package com.example.demo.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.DemoApplicationTests;
import com.example.demo.controller.BlogController;
import com.example.demo.dto.*;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.serviceimpl.BlogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.ArrayStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
        mockMvc.perform(get("/blogs?blog_id=1").header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
    }
    //直接null可以吗。。。又没对返回的东西做加工啥的。。

    @Test
    public void testAddBlog() throws Exception {
        Mockito.when(blogService.addBlog(Mockito.any())).thenReturn(null);
        BlogContentDTO blogContentDTO = new BlogContentDTO("123456", null);
        String requestJson = JSONObject.toJSONString(blogContentDTO);
        mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).addBlog(Mockito.any());
    }

    @Test
    public void testUpdateBlog() throws Exception {
        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
        BlogPutDTO blogPutDTO = new BlogPutDTO(1, "dest");
        String requestJson = JSONObject.toJSONString(blogPutDTO);
        mockMvc.perform(put("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).updateBlog(Mockito.any());
    }

    @Test
    public void testCheckReportedBlog() throws Exception {
        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
        BlogCheckDTO blogCheckDTO = new BlogCheckDTO(1, 1);
        String requestJson = JSONObject.toJSONString(blogCheckDTO);
        mockMvc.perform(put("/blogs/reported")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).findBlogByBlog_id(any());
        verify(blogService, times(1)).updateBlog(Mockito.any());
    }

    @Test
    public void testDeleteBlog() throws Exception {
        doNothing().when(blogService).deleteByBlog_id(Mockito.any());
        mockMvc.perform(delete("/blogs?blog_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).deleteByBlog_id(1);
    }

    @Test
    public void testDeleteComment() throws Exception {
        doNothing().when(blogService).deleteCommentByComment_id(Mockito.any());
        mockMvc.perform(delete("/blogs/comments?comment_id=1")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).deleteCommentByComment_id(any());
    }

    @Test
    public void testVote() throws Exception {
        doNothing().when(blogService).incrVoteCount(Mockito.any());
        doNothing().when(blogService).incrCommentVoteCount(Mockito.any());
        VoteDTO voteDTO = new VoteDTO(1,-1);
        String requestJson = JSONObject.toJSONString(voteDTO);
        mockMvc.perform(post("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrVoteCount(Mockito.any());
        VoteDTO voteDTO1 = new VoteDTO(2,1);
        String requestJson1 = JSONObject.toJSONString(voteDTO1);
        mockMvc.perform(post("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson1)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).incrCommentVoteCount(Mockito.any());
    }

    @Test
    public void testCancelVote() throws Exception {
        doNothing().when(blogService).decrVoteCount(Mockito.any());
        doNothing().when(blogService).decrCommentVoteCount(Mockito.any());
        VoteDTO voteDTO = new VoteDTO(1,-1);
        String requestJson = JSONObject.toJSONString(voteDTO);
        mockMvc.perform(delete("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).decrVoteCount(Mockito.any());
        VoteDTO voteDTO1 = new VoteDTO(2,1);
        String requestJson1 = JSONObject.toJSONString(voteDTO1);
        mockMvc.perform(delete("/blogs/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson1)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).decrCommentVoteCount(Mockito.any());
    }

    @Test
    public void testComment() throws Exception {
        Mockito.when(blogService.addBlogComment(Mockito.any())).thenReturn(null);
        CommentPostDTO commentPostDTO = new CommentPostDTO(1, 1, "dd", "ddd", "test");
        String requestJson = JSONObject.toJSONString(commentPostDTO);
        mockMvc.perform(post("/blogs/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
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
        mockMvc.perform(get("/blogs/search?keyword=abbc"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getAllBlogs();
        verify(blogService, times(1)).getSimpleBlogDetail(Mockito.any());
    }

    @Test
    public void testGetReportedBlogs() throws Exception{
        List<BlogCount> blogCounts = new ArrayList<>();
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        Mockito.when(blogService.getAllReportedBlogs()).thenReturn(blogCounts);
        Mockito.when(blogService.getSimpleBlogDetail(any())).thenReturn(null);
        mockMvc.perform(get("/blogs/reported"))
                .andExpect(status().isOk()).andReturn();
        verify(blogService, times(1)).getAllReportedBlogs();
        verify(blogService, times(2)).getSimpleBlogDetail(Mockito.any());

    }
}
