package com.example.amoy_interest.dao;

import com.example.amoy_interest.dto.BlogHeatParam;
import com.example.amoy_interest.entity.BlogCount;

import java.util.Date;
import java.util.List;

public interface BlogCountDao {
    BlogCount findBlogCountByBlog_id(Integer blog_id);
    List<BlogCount> findReportedBlogs();
    BlogCount saveBlogCount(BlogCount blogCount);
    void saveAll(List<BlogCount> list);
    void incrVoteCount(Integer blog_id);
    void decrVoteCount(Integer blog_id);

    List<BlogHeatParam> getAllBlogHeatParam();

    List<BlogHeatParam> getBlogHeatParamAfterTime(Date time);
}
