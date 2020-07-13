package com.example.demo.daoimpl;

import com.example.demo.dao.TopicDao;
import com.example.demo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDaoImpl implements TopicDao {
    @Autowired
    TopicRepository topicRepository;
}
