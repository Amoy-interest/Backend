package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogCount;

public interface BlogCountDao {
    BlogCount findBlogCountByBlog_id(Integer blog_id);
    BlogCount saveBlogCount(BlogCount blogCount);
    void incrVoteCount(Integer blog_id);
}
