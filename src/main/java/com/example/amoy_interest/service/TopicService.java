package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;

import java.util.List;

public interface TopicService {
//    TopicDTO getTopicById(int topic_id);
    TopicDTO getTopicByName(String topic_name);
    List<TopicReportDTO> getReportedTopics();
    boolean CheckReportedTopic(TopicCheckDTO topicCheckDTO);
}
