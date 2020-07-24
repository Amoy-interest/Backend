package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogImage;

import java.util.List;

public interface BlogImageDao {
    List<BlogImage> findBlogImageByBlog_id(Integer blog_id);
    BlogImage save(BlogImage blogImage);
}
