package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {
//    TopicDTO getTopicById(int topic_id);
    Integer getTopic_idByName(String topic_name);
    TopicDTO getTopicDTOByName(String topic_name);
    List<TopicReportDTO> getReportedTopics();
    boolean checkReportedTopic(TopicCheckDTO topicCheckDTO);
    boolean reportTopicByName(String topic_name);
    Page<TopicReportDTO> getReportedTopicsPage(Integer pageNum,Integer pageSize);

}
