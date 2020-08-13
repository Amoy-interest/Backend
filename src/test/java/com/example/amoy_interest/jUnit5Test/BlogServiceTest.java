package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.daoimpl.BlogCommentDaoImpl;
import com.example.amoy_interest.daoimpl.BlogCountDaoImpl;
import com.example.amoy_interest.daoimpl.BlogDaoImpl;
import com.example.amoy_interest.daoimpl.BlogImageDaoImpl;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
public class BlogServiceTest{
    @InjectMocks
    private BlogServiceImpl blogService;

    @Mock
    private BlogDaoImpl blogDao;
    @Mock
    private BlogCountDaoImpl blogCountDao;
    @Mock
    private BlogImageDaoImpl blogImageDao;
    @Mock
    private BlogCommentDaoImpl blogCommentDao;
    @Mock
    private UserDao userDao;

////    @Test
////    public void testAddBlog() {
////        when(blogDao.saveBlog(any())).thenReturn(null);
//////        blogService.addBlog(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
////        verify(blogDao, times(1)).saveBlog(any());
////    }
//
//    @Test
//    public void testUpdateBlog() {
//        Blog blog = new Blog(1,1,1,0,new Date(),"我不好",false,0,0);
//        User user = new User(1,"mok","da@qq.com",0,null,100,null,null);
//        blog.setUser(user);
//        blog.setBlogCount(new BlogCount(1,0,0,0,0));
//        blog.setBlogImages(null);
//        when(blogDao.findBlogByBlog_id(1)).thenReturn(blog);
//        Blog blog1 = new Blog(1,1,1,0,new Date(),"你好啊",false,0,0);
//        blog1.setUser(user);
//        blog1.setBlogCount(new BlogCount(1,0,0,0,0));
//        blog1.setBlogImages(null);
//        when(blogDao.saveBlog(blog1)).thenReturn(blog1);
//        blogService.updateBlog(new BlogPutDTO(1,"你好啊",null));
//        verify(blogDao, times(1)).saveBlog(any());
//    }
//
//    @Test
//    public void testDeleteByBlog_id() {
//        doNothing().when(blogDao).deleteByBlog_id(1);
//        blogService.deleteByBlog_id(1);
//        verify(blogDao, times(1)).deleteByBlog_id(any());
//    }
//
//    @Test
//    public void testDeleteCommentByComment_id() {
//        doNothing().when(blogCommentDao).deleteByComment_id(1);
//        blogService.deleteCommentByComment_id(1);
//        verify(blogCommentDao, times(1)).deleteByComment_id(any());
//    }
//
//    @Test
//    public void testIncrVoteCount() {
//        doNothing().when(blogCountDao).incrVoteCount(1);
//        blogService.incrVoteCount(1);
//        verify(blogCountDao, times(1)).incrVoteCount(any());
//    }
//
//    @Test
//    public void testDecrVoteCount() {
//        doNothing().when(blogCountDao).decrVoteCount(1);
//        blogService.decrVoteCount(1);
//        verify(blogCountDao, times(1)).decrVoteCount(any());
//    }
//
//    @Test
//    public void testIncrCommentVoteCount() {
//        doNothing().when(blogCommentDao).incrCommentVoteCount(1);
//        blogService.incrCommentVoteCount(1);
//        verify(blogCommentDao, times(1)).incrCommentVoteCount(any());
//    }
//
//    @Test
//    public void testDecrCommentVoteCount() {
//        doNothing().when(blogCommentDao).decrCommentVoteCount(1);
//        blogService.decrCommentVoteCount(1);
//        verify(blogCommentDao, times(1)).decrCommentVoteCount(any());
//    }
//
////    @Test
////    public void testAddBlogComment() {
////        when(blogCommentDao.saveBlogComment(any())).thenReturn(null);
////
//////        blogService.addBlogComment(new BlogComment(1, 1, 1,"explodingnerk", null, 1, "求求你练下力量吧", null, 5, false, -1));
////        verify(blogCommentDao, times(1)).saveBlogComment(any());
////    }
//
//    @Test
//    public void testFindBlogByBlog_id() {
//        Blog blog = new Blog(1, 1, 0, 0,null, "666",  false, 1, -1);
//        when(blogDao.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
//        assertEquals(blog, blogService.findBlogByBlog_id(1));
//    }
//    //这种比较简单的直接调用DAO层接口的就不测试了。。
//
////    @Test
////    public void testGetSimpleBlogDetail() {
////        when(blogDao.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
////        when(blogDao.findBlogByBlog_id(2)).thenReturn(new Blog(2, 1, 0, 1,null, "2333",  false, 1, 1));
////        when(blogCountDao.findBlogCountByBlog_id(1)).thenReturn(new BlogCount(1, 2, 3, 4, 5));
////        when(blogCountDao.findBlogCountByBlog_id(2)).thenReturn(new BlogCount(2, 20, 30, 40, 50));
////        List<BlogImage>images = new ArrayList<>();
////        images.add(new BlogImage(1, "test.jpg"));
////        when(blogImageDao.findBlogImageByBlog_id(1)).thenReturn(images);
////        when(blogImageDao.findBlogImageByBlog_id(2)).thenReturn(null);
////
////        // blogContent  blogChild blogCount blogComment先为空吧。。。
////        BlogDTO blogDTO = blogService.getSimpleBlogDetail(1);  //原创 blog_id = 1
////        assertEquals(0, (long)blogDTO.getBlog_type());
////        assertNull(blogDTO.getBlog_child());
////        assertEquals("666", blogDTO.getBlog_content().getText());
////        assertEquals(Collections.singletonList("test.jpg"), blogDTO.getBlog_content().getImages());
////        assertEquals(2, (long)blogDTO.getBlog_count().getForward_count());
////        assertEquals(3, (long)blogDTO.getBlog_count().getComment_count());
////        assertEquals(4, (long)blogDTO.getBlog_count().getVote_count());
//////        assertEquals(5, (long)blogDTO.getBlog_count().getReport_count());
//////        assertNull(blogDTO.getBlog_comments());
////
////        BlogDTO blogDTO1 = blogService.getSimpleBlogDetail(2);  //转发 blog_id = 1 的blog
////        assertEquals(1, (long)blogDTO1.getBlog_type());
//////        assertEquals("666", blogDTO1.getBlog_child().getText());
//////        assertEquals(Collections.singletonList("test.jpg"), blogDTO1.getBlog_child().getImages());
////        assertEquals("2333", blogDTO1.getBlog_content().getText());
////        assertNull(blogDTO1.getBlog_content().getImages());
////        assertEquals(20, (long)blogDTO1.getBlog_count().getForward_count());
////        assertEquals(30, (long)blogDTO1.getBlog_count().getComment_count());
////        assertEquals(40, (long)blogDTO1.getBlog_count().getVote_count());
//////        assertEquals(50, (long)blogDTO1.getBlog_count().getReport_count());
//////        assertNull(blogDTO1.getBlog_comments());
////    }
//
////    @Test
////    public void testGetAllBlogDetail() {
////        User user = new User(1,"mok","dnfn@sjtu.edu.cn",0,null,100,null,null);
////        Blog blog1 = new Blog(1, 1, 0, 0,null, "666",  false, 1, 0);
////        Blog blog2 = new Blog(2, 1, 0, 1,null, "2333",  false, 1, 1);
////        blog1.setUser(user);
////        blog1.setBlogCount(new BlogCount(1,0,0,0,0));
////        blog2.setUser(user);
////        blog2.setBlogCount(new BlogCount(2,0,0,0,0));
////        when(blogDao.findBlogByBlog_id(1)).thenReturn(blog1);
////        when(blogDao.findBlogByBlog_id(2)).thenReturn(blog2);
////        when(blogCountDao.findBlogCountByBlog_id(1)).thenReturn(new BlogCount(1, 2, 3, 4, 5));
////        when(blogCountDao.findBlogCountByBlog_id(2)).thenReturn(new BlogCount(2, 20, 30, 40, 50));
////        List<BlogImage>images = new ArrayList<>();
////        images.add(new BlogImage(1, "test.jpg"));
////        when(blogImageDao.findBlogImageByBlog_id(1)).thenReturn(images);
////        when(blogImageDao.findBlogImageByBlog_id(2)).thenReturn(null);
////        List<BlogComment> blogComments = new ArrayList<>();
//////        blogComments.add(new BlogComment(1, 1, "explodingnerk", null, 1, "求求你练下力量吧", null, 5, false, -1));
////        when(blogCommentDao.findLevel1CommentByBlog_id(1)).thenReturn(blogComments);
////        when(blogCommentDao.findLevel1CommentByBlog_id(2)).thenReturn(blogComments);
////
////        // blogContent  blogChild blogCount blogComment先为空吧。。。
////        BlogDTO blogDTO = blogService.getAllBlogDetail(1);  //原创 blog_id = 1
////        assertEquals(0, (long)blogDTO.getBlog_type());
////        assertNull(blogDTO.getBlog_child());
////        assertEquals("666", blogDTO.getBlog_content().getText());
////        assertEquals(Collections.singletonList("test.jpg"), blogDTO.getBlog_content().getImages());
////        assertEquals(2, (long)blogDTO.getBlog_count().getForward_count());
////        assertEquals(3, (long)blogDTO.getBlog_count().getComment_count());
////        assertEquals(4, (long)blogDTO.getBlog_count().getVote_count());
//////        assertEquals(5, (long)blogDTO.getBlog_count().getReport_count());
//////        assertEquals(1, blogDTO.getBlog_comments().size());
////
////        BlogDTO blogDTO1 = blogService.getAllBlogDetail(2);  //转发 blog_id = 1 的blog
////        assertEquals(1, (long)blogDTO1.getBlog_type());
//////        assertEquals("666", blogDTO1.getBlog_child().getText());
//////        assertEquals(Collections.singletonList("test.jpg"), blogDTO1.getBlog_child().getImages());
////        assertEquals("2333", blogDTO1.getBlog_content().getText());
////        assertNull(blogDTO1.getBlog_content().getImages());
////        assertEquals(20, (long)blogDTO1.getBlog_count().getForward_count());
////        assertEquals(30, (long)blogDTO1.getBlog_count().getComment_count());
////        assertEquals(40, (long)blogDTO1.getBlog_count().getVote_count());
//////        assertEquals(50, (long)blogDTO1.getBlog_count().getReport_count());
//////        assertEquals(1, blogDTO1.getBlog_comments().size());
////    }
//
//    @Test
//    public void testGetAllBlogs() {
//        List<Blog> blogs = new ArrayList<>();
//        blogs.add(new Blog(1, 1, 1, 1, null, "test", false, 1, 1));
//        blogs.add(new Blog(1, 1, 1, 1, null, "test", false, 1, 1));
//        when(blogDao.getAllBlogs()).thenReturn(blogs);
//        List<Blog> blogList = blogService.getAllBlogs();
//        assertEquals(2, blogList.size());
//    }
//
//    @Test
//    public void testGetAllReportedBlogs() {
//        List<BlogCount> blogCounts = new ArrayList<>();
//        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
//        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
//        when(blogCountDao.findReportedBlogs()).thenReturn(blogCounts);
//        assertEquals(2, blogService.getAllReportedBlogs().size());
//    }
}
