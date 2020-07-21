package com.example.amoy_interest.dao;


import com.example.amoy_interest.entity.Topic;

import java.util.List;

public interface TopicDao {
    Topic getTopicById(int topic_id);
    Topic insert(Topic topic);
    void update(Topic topic);
    Topic getTopicByName(String topic_name);
    List<Topic> getReportedTopic();
}
