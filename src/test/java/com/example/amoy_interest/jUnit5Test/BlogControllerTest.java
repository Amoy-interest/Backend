package com.example.amoy_interest.jUnit5Test;
import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.BlogServiceImpl;
import com.example.amoy_interest.serviceimpl.TopicServiceImpl;
import com.example.amoy_interest.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest{

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @MockBean
    private BlogService blogService;

//    @InjectMocks
//    private BlogController blogController;
//
//    @Test
//    public void testGetBlog() throws Exception {
//
//        Mockito.when(blogService.getAllBlogDetail(1)).thenReturn(null);
//        mockMvc.perform(get("/blogs?blog_id=1").header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//    }
//    //直接null可以吗。。。又没对返回的东西做加工啥的。。
//
//    @Test
//    public void testAddBlog() throws Exception {
//        Mockito.when(blogService.addBlog(Mockito.any())).thenReturn(null);
//        BlogContentDTO blogContentDTO = new BlogContentDTO("123456", null);
//        String requestJson = JSONObject.toJSONString(blogContentDTO);
//        mockMvc.perform(post("/blogs")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).addBlog(Mockito.any());
//    }
//
//    @Test
//    public void testUpdateBlog() throws Exception {
//        Mockito.when(blogService.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
//        Mockito.when(blogService.updateBlog(Mockito.any())).thenReturn(null);
//        BlogPutDTO blogPutDTO = new BlogPutDTO(1, "dest",null);
//        String requestJson = JSONObject.toJSONString(blogPutDTO);
//        mockMvc.perform(put("/blogs")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).updateBlog(Mockito.any());
//    }
//
//    @Test
//    public void testDeleteBlog() throws Exception {
//        doNothing().when(blogService).deleteByBlog_id(Mockito.any());
//        mockMvc.perform(delete("/blogs?blog_id=1")
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).deleteByBlog_id(1);
//    }
//
//    @Test
//    public void testDeleteComment() throws Exception {
//        doNothing().when(blogService).deleteCommentByComment_id(any());
//        mockMvc.perform(delete("/blogs/comments?comment_id=1")
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).deleteCommentByComment_id(any());
//    }
//
//    @Test
//    public void testVote() throws Exception {
//        doNothing().when(blogService).incrVoteCount(Mockito.any());
//        doNothing().when(blogService).incrCommentVoteCount(Mockito.any());
//        VoteDTO voteDTO = new VoteDTO(1,0);
//        String requestJson = JSONObject.toJSONString(voteDTO);
//        mockMvc.perform(post("/blogs/vote")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).incrVoteCount(Mockito.any());
//        VoteDTO voteDTO1 = new VoteDTO(2,1);
//        String requestJson1 = JSONObject.toJSONString(voteDTO1);
//        mockMvc.perform(post("/blogs/vote")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson1)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).incrCommentVoteCount(Mockito.any());
//    }
//
//    @Test
//    public void testCancelVote() throws Exception {
//        doNothing().when(blogService).decrVoteCount(Mockito.any());
//        doNothing().when(blogService).decrCommentVoteCount(Mockito.any());
//        VoteDTO voteDTO = new VoteDTO(1,0);
//        String requestJson = JSONObject.toJSONString(voteDTO);
//        mockMvc.perform(delete("/blogs/vote")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).decrVoteCount(Mockito.any());
//        VoteDTO voteDTO1 = new VoteDTO(2,1);
//        String requestJson1 = JSONObject.toJSONString(voteDTO1);
//        mockMvc.perform(delete("/blogs/vote")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson1)
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService, times(1)).decrCommentVoteCount(Mockito.any());
//    }
//
//    @Test
//    public void testComment() throws Exception {
//        when(blogService.addBlogComment(Mockito.any())).thenReturn(null);
////        CommentPostDTO commentPostDTO = new CommentPostDTO(1, 1, "dd", "ddd", "test");
////        String requestJson = JSONObject.toJSONString(commentPostDTO);
////        mockMvc.perform(post("/blogs/comments")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(requestJson)
////                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
////                .andExpect(status().isOk()).andReturn();
////        verify(blogService, times(1)).addBlogComment(Mockito.any());
//    }
//
//    @Test
//    public void testSearch() throws Exception {
//        List<BlogDTO> blogList = new ArrayList<>();
////        blogList.add(new Blog(1, 1, 0, 0,null, "abbcdde",  false, 1, 0));
////        blogList.add(new Blog(2, 1, 0, 0,null, "abcdde",  false, 1, 0));
//        Pageable pageable = PageRequest.of(0,5);
//        Page<BlogDTO> page = new PageImpl<>(blogList,pageable,0);
//        Mockito.when(blogService.getSearchListByBlog_text("abbc",0,5)).thenReturn(page);
//        mockMvc.perform(get("/blogs/search?keyword=abbc")
//                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjAsInVzZXJfaWQiOjEsImlzcyI6ImF1dGgwIiwiZXhwIjoxNTk1NjQ2OTQyfQ.8Ycii-oG6JtxOO1DGTqdAJV1FOUWpvEJyYOTCBc06Us"))
//                .andExpect(status().isOk()).andReturn();
//        verify(blogService,times(1)).getSearchListByBlog_text("abbc",0,5);
//    }
}
