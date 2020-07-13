package com.example.demo.serviceimpl;

import com.example.demo.dao.TopicDao;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicDao topicDao;
}
