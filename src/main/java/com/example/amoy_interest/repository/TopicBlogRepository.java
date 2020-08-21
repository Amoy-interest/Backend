package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.TopicBlog;
import com.example.amoy_interest.entity.TopicBlogPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Mok
 * @Date: 2020/8/19 9:27
 */
public interface TopicBlogRepository extends JpaRepository<TopicBlog, TopicBlogPK> {

}
