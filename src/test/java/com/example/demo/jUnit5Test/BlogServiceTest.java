package com.example.demo;

import com.example.demo.dao.BlogCountDao;
import com.example.demo.dao.BlogDao;
import com.example.demo.dao.BlogImageDao;
import com.example.demo.entity.Blog;
import com.example.demo.serviceimpl.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class BlogServiceTest extends DemoApplicationTests{
    @InjectMocks
    private BlogServiceImpl blogService;

    @Mock
    private BlogDao blogDao;
    @Mock
    private BlogCountDao blogCountDao;
    @Mock
    private BlogImageDao blogImageDao;

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
        when(blogDao.findBlogByBlog_id(2)).thenReturn(new Blog(2, 1, 0, 1,null, "666",  false, 1, 1));
    }
}
