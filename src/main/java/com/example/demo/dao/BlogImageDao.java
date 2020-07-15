package com.example.demo.dao;

import com.example.demo.entity.BlogImage;

import java.util.List;

public interface BlogImageDao {
    List<BlogImage> findBlogImageByBlog_id(Integer blog_id);
}
