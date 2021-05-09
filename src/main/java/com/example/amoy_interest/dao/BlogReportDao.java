package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.entity.BlogVote;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:47
 */
public interface BlogReportDao {
    void save(BlogReport blogReport);
    void saveAll(List<BlogReport> list);
    BlogReport getByBlogIdAndUserId(Integer blog_id,Integer user_id);
}
