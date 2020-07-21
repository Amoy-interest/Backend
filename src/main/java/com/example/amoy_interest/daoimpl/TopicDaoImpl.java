package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TopicDaoImpl implements TopicDao {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic getTopicById(int topic_id) {
        return topicRepository.getOne(topic_id);
    }

    @Override
    public Topic insert(Topic topic) {
        return topicRepository.saveAndFlush(topic);
    }

    @Override
    public void update(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public Topic getTopicByName(String topic_name) {
        return topicRepository.getTopicByName(topic_name);
    }

    @Override
    public List<Topic> getReportedTopic() {
        return topicRepository.getReportedTopic();
    }

    @Override
    public Page<Topic> getReportedTopicPage(Pageable pageable) {
        return topicRepository.getReportedTopicPage(pageable);
    }
}
