package com.example.demo.dao;


import com.example.demo.entity.Topic;

public interface TopicDao {
    Topic getTopicById(int topic_id);
    Topic insert(Topic topic);
    void update(Topic topic);
    Topic getTopicByName(String topic_name);
}
