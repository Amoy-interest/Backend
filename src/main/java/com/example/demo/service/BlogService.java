package com.example.demo.service;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;

public interface BlogService {
    Blog addBlog(Blog blog);
    Blog updateBlog(Blog blog);

    Blog findBlogByBlog_id(Integer blog_id);
    void deleteByBlog_id(Integer blog_id);
}
