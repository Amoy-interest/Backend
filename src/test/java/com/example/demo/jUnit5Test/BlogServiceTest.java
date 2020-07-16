package com.example.demo.jUnit5Test;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dao.BlogCommentDao;
import com.example.demo.dao.BlogCountDao;
import com.example.demo.dao.BlogDao;
import com.example.demo.dao.BlogImageDao;
import com.example.demo.dto.BlogCountDTO;
import com.example.demo.dto.BlogDTO;
import com.example.demo.entity.*;
import com.example.demo.serviceimpl.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BlogServiceTest extends DemoApplicationTests {
    @InjectMocks
    private BlogServiceImpl blogService;

    @Mock
    private BlogDao blogDao;
    @Mock
    private BlogCountDao blogCountDao;
    @Mock
    private BlogImageDao blogImageDao;
    @Mock
    private BlogCommentDao blogCommentDao;

    @Test
    public void testAddBlog() {
        when(blogDao.saveBlog(any())).thenReturn(null);
        blogService.addBlog(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        verify(blogDao, times(1)).saveBlog(any());
    }

    @Test
    public void testUpdateBlog() {
        when(blogDao.saveBlog(any())).thenReturn(null);
        blogService.updateBlog(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        verify(blogDao, times(1)).saveBlog(any());
    }

    @Test
    public void testDeleteByBlog_id() {
        doNothing().when(blogDao).deleteByBlog_id(1);
        blogService.deleteByBlog_id(1);
        verify(blogDao, times(1)).deleteByBlog_id(any());
    }

    @Test
    public void testDeleteCommentByComment_id() {
        doNothing().when(blogCommentDao).deleteByComment_id(1);
        blogService.deleteCommentByComment_id(1);
        verify(blogCommentDao, times(1)).deleteByComment_id(any());
    }

    @Test
    public void testIncrVoteCount() {
        doNothing().when(blogCountDao).incrVoteCount(1);
        blogService.incrVoteCount(1);
        verify(blogCountDao, times(1)).incrVoteCount(any());
    }

    @Test
    public void testDecrVoteCount() {
        doNothing().when(blogCountDao).decrVoteCount(1);
        blogService.decrVoteCount(1);
        verify(blogCountDao, times(1)).decrVoteCount(any());
    }

    @Test
    public void testIncrCommentVoteCount() {
        doNothing().when(blogCommentDao).incrCommentVoteCount(1);
        blogService.incrCommentVoteCount(1);
        verify(blogCommentDao, times(1)).incrCommentVoteCount(any());
    }

    @Test
    public void testDecrCommentVoteCount() {
        doNothing().when(blogCommentDao).decrCommentVoteCount(1);
        blogService.decrCommentVoteCount(1);
        verify(blogCommentDao, times(1)).decrCommentVoteCount(any());
    }

    @Test
    public void testAddBlogComment() {
        when(blogCommentDao.saveBlogComment(any())).thenReturn(null);
        blogService.addBlogComment(new BlogComment(1, 1, "explodingnerk", null, 1, "求求你练下力量吧", null, 5, false, -1));
        verify(blogCommentDao, times(1)).saveBlogComment(any());
    }

    @Test
    public void testFindBlogByBlog_id() {
        Blog blog = new Blog(1, 1, 0, 0,null, "666",  false, 1, -1);
        when(blogDao.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        Assert.assertEquals(blog, blogService.findBlogByBlog_id(1));
    }
    //这种比较简单的直接调用DAO层接口的就不测试了。。

    @Test
    public void testGetSimpleBlogDetail() {
        when(blogDao.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        when(blogDao.findBlogByBlog_id(2)).thenReturn(new Blog(2, 1, 0, 1,null, "2333",  false, 1, 1));
        when(blogCountDao.findBlogCountByBlog_id(1)).thenReturn(new BlogCount(1, 2, 3, 4, 5));
        when(blogCountDao.findBlogCountByBlog_id(2)).thenReturn(new BlogCount(2, 20, 30, 40, 50));
        List<BlogImage>images = new ArrayList<>();
        images.add(new BlogImage(1, "test.jpg"));
        when(blogImageDao.findBlogImageByBlog_id(1)).thenReturn(images);
        when(blogImageDao.findBlogImageByBlog_id(2)).thenReturn(null);

        // blogContent  blogChild blogCount blogComment先为空吧。。。
        BlogDTO blogDTO = blogService.getSimpleBlogDetail(1);  //原创 blog_id = 1
        Assert.assertEquals(0, (long)blogDTO.getBlog_type());
        Assert.assertNull(blogDTO.getBlog_child());
        Assert.assertEquals("666", blogDTO.getBlog_content().getText());
        Assert.assertEquals(Collections.singletonList("test.jpg"), blogDTO.getBlog_content().getImages());
        Assert.assertEquals(2, (long)blogDTO.getBlog_count().getForward_count());
        Assert.assertEquals(3, (long)blogDTO.getBlog_count().getComment_count());
        Assert.assertEquals(4, (long)blogDTO.getBlog_count().getVote_count());
        Assert.assertEquals(5, (long)blogDTO.getBlog_count().getReport_count());
        Assert.assertNull(blogDTO.getBlog_comments());

        BlogDTO blogDTO1 = blogService.getSimpleBlogDetail(2);  //转发 blog_id = 1 的blog
        Assert.assertEquals(1, (long)blogDTO1.getBlog_type());
        Assert.assertEquals("666", blogDTO1.getBlog_child().getText());
        Assert.assertEquals(Collections.singletonList("test.jpg"), blogDTO1.getBlog_child().getImages());
        Assert.assertEquals("2333", blogDTO1.getBlog_content().getText());
        Assert.assertNull(blogDTO1.getBlog_content().getImages());
        Assert.assertEquals(20, (long)blogDTO1.getBlog_count().getForward_count());
        Assert.assertEquals(30, (long)blogDTO1.getBlog_count().getComment_count());
        Assert.assertEquals(40, (long)blogDTO1.getBlog_count().getVote_count());
        Assert.assertEquals(50, (long)blogDTO1.getBlog_count().getReport_count());
        Assert.assertNull(blogDTO1.getBlog_comments());
    }

    @Test
    public void testGetAllBlogDetail() {
        when(blogDao.findBlogByBlog_id(1)).thenReturn(new Blog(1, 1, 0, 0,null, "666",  false, 1, -1));
        when(blogDao.findBlogByBlog_id(2)).thenReturn(new Blog(2, 1, 0, 1,null, "2333",  false, 1, 1));
        when(blogCountDao.findBlogCountByBlog_id(1)).thenReturn(new BlogCount(1, 2, 3, 4, 5));
        when(blogCountDao.findBlogCountByBlog_id(2)).thenReturn(new BlogCount(2, 20, 30, 40, 50));
        List<BlogImage>images = new ArrayList<>();
        images.add(new BlogImage(1, "test.jpg"));
        when(blogImageDao.findBlogImageByBlog_id(1)).thenReturn(images);
        when(blogImageDao.findBlogImageByBlog_id(2)).thenReturn(null);
        List<BlogComment> blogComments = new ArrayList<>();
        blogComments.add(new BlogComment(1, 1, "explodingnerk", null, 1, "求求你练下力量吧", null, 5, false, -1));
        when(blogCommentDao.findLevel1CommentByBlog_id(1)).thenReturn(blogComments);
        when(blogCommentDao.findLevel1CommentByBlog_id(2)).thenReturn(blogComments);

        // blogContent  blogChild blogCount blogComment先为空吧。。。
        BlogDTO blogDTO = blogService.getAllBlogDetail(1);  //原创 blog_id = 1
        Assert.assertEquals(0, (long)blogDTO.getBlog_type());
        Assert.assertNull(blogDTO.getBlog_child());
        Assert.assertEquals("666", blogDTO.getBlog_content().getText());
        Assert.assertEquals(Collections.singletonList("test.jpg"), blogDTO.getBlog_content().getImages());
        Assert.assertEquals(2, (long)blogDTO.getBlog_count().getForward_count());
        Assert.assertEquals(3, (long)blogDTO.getBlog_count().getComment_count());
        Assert.assertEquals(4, (long)blogDTO.getBlog_count().getVote_count());
        Assert.assertEquals(5, (long)blogDTO.getBlog_count().getReport_count());
        Assert.assertEquals(1, blogDTO.getBlog_comments().size());

        BlogDTO blogDTO1 = blogService.getAllBlogDetail(2);  //转发 blog_id = 1 的blog
        Assert.assertEquals(1, (long)blogDTO1.getBlog_type());
        Assert.assertEquals("666", blogDTO1.getBlog_child().getText());
        Assert.assertEquals(Collections.singletonList("test.jpg"), blogDTO1.getBlog_child().getImages());
        Assert.assertEquals("2333", blogDTO1.getBlog_content().getText());
        Assert.assertNull(blogDTO1.getBlog_content().getImages());
        Assert.assertEquals(20, (long)blogDTO1.getBlog_count().getForward_count());
        Assert.assertEquals(30, (long)blogDTO1.getBlog_count().getComment_count());
        Assert.assertEquals(40, (long)blogDTO1.getBlog_count().getVote_count());
        Assert.assertEquals(50, (long)blogDTO1.getBlog_count().getReport_count());
        Assert.assertEquals(1, blogDTO1.getBlog_comments().size());
    }

    @Test
    public void testGetAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog(1, 1, 1, 1, null, "test", false, 1, 1));
        blogs.add(new Blog(1, 1, 1, 1, null, "test", false, 1, 1));
        when(blogDao.getAllBlogs()).thenReturn(blogs);
        List<Blog> blogList = blogService.getAllBlogs();
        Assert.assertEquals(2, blogList.size());
    }

    @Test
    public void testGetAllReportedBlogs() {
        List<BlogCount> blogCounts = new ArrayList<>();
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        blogCounts.add(new BlogCount(1, 1, 1, 1, 1));
        when(blogCountDao.findReportedBlogs()).thenReturn(blogCounts);
        Assert.assertEquals(2, blogService.getAllReportedBlogs().size());
    }
}
