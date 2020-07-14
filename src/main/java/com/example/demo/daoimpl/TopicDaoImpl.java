package com.example.demo.daoimpl;

import com.example.demo.dao.TopicDao;
import com.example.demo.entity.Topic;
import com.example.demo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.OneToMany;

@Repository
public class TopicDaoImpl implements TopicDao {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic getTopicById(int topic_id) {
        return topicRepository.getOne(topic_id);
    }
}
