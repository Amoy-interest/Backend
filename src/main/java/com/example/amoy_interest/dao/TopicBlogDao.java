package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.TopicBlog;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/19 9:26
 */
public interface TopicBlogDao {
    List<TopicBlog> saveAll(List<TopicBlog> list);
}
