package com.example.amoy_interest.dao;


import com.example.amoy_interest.dto.TopicHeatParam;
import com.example.amoy_interest.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicDao {
    Topic getTopicById(Integer topic_id);
    Topic insert(Topic topic);
    Topic update(Topic topic);
    Topic getTopicByName(String topic_name);
    List<Topic> getTopicListByName(List<String> topic_name);
    List<Topic> getReportedTopic();
    Page<Topic> getReportedTopicPage(Pageable pageable);
    Page<Topic> searchReportedTopicPage(String keyword,Pageable pageable);
    Page<String> searchTopicsPage(String keyword, Pageable pageable);
    List<TopicHeatParam> getAllTopicCount();
}
