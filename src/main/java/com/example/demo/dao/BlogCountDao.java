package com.example.demo.dao;

import com.example.demo.entity.BlogCount;

public interface BlogCountDao {
    BlogCount findBlogCountByBlog_id(Integer blog_id);
    BlogCount saveBlogCount(BlogCount blogCount);
    void incrVoteCount(Integer blog_id);
}
