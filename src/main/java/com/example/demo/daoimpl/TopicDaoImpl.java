package com.example.demo.daoimpl;

import com.example.demo.dao.TopicDao;
import com.example.demo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicDaoImpl implements TopicDao {
    @Autowired
    TopicRepository topicRepository;
}
