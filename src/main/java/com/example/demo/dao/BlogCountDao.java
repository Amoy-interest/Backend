package com.example.demo.dao;

import com.example.demo.entity.BlogCount;

import java.util.List;

public interface BlogCountDao {
    BlogCount findBlogCountByBlog_id(Integer blog_id);
    List<BlogCount> findReportedBlogs();
    BlogCount saveBlogCount(BlogCount blogCount);
    void incrVoteCount(Integer blog_id);
    void decrVoteCount(Integer blog_id);
}
