package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.Blog;

import java.util.List;

public interface BlogDao {
    Blog findBlogByBlog_id(Integer blog_id);
    Blog saveBlog(Blog blog);
    void deleteByBlog_id(Integer blog_id);
    List<Blog> getAllBlogs();
}
