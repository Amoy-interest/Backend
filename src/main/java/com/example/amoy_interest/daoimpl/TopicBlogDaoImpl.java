package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.TopicBlogDao;
import com.example.amoy_interest.entity.TopicBlog;
import com.example.amoy_interest.repository.TopicBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/19 9:27
 */
@Repository
public class TopicBlogDaoImpl implements TopicBlogDao {
    @Autowired
    private TopicBlogRepository topicBlogRepository;

    @Override
    public List<TopicBlog> saveAll(List<TopicBlog> list) {
        return topicBlogRepository.saveAll(list);
    }
}
