package com.example.demo.serviceimpl;

import com.example.demo.dao.TopicDao;
import com.example.demo.dto.TopicDTO;
import com.example.demo.entity.Topic;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicDao topicDao;

    @Override
    public TopicDTO getTopicById(int topic_id) {
        Topic topic = topicDao.getTopicById(topic_id);
        return null;
    }
}
