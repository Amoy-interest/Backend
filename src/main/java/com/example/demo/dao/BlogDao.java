package com.example.demo.dao;

import com.example.demo.entity.Blog;

import java.util.List;

public interface BlogDao {
    Blog findBlogByBlog_id(Integer blog_id);
    Blog saveBlog(Blog blog);
    void deleteByBlog_id(Integer blog_id);
    List<Blog> getAllBlogs();
}
