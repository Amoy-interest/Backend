package com.example.demo.dao;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;

import java.util.List;

public interface BlogDao {
    Blog findBlogByBlog_id(Integer blog_id);
    Blog saveBlog(Blog blog);
    void deleteByBlog_id(Integer blog_id);
    List<Blog> getAllBlogs();
}
