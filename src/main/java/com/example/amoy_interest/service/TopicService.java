package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {
//    TopicDTO getTopicById(int topic_id);
    Integer getTopic_idByName(String topic_name);
    TopicDTO getTopicDTOByName(String topic_name);
    List<TopicReportDTO> getReportedTopics();
    boolean checkReportedTopic(TopicCheckDTO topicCheckDTO);
    boolean reportTopicByName(String topic_name);
    Page<TopicReportDTO> getReportedTopicsPage(Integer pageNum,Integer pageSize,Integer orderType);
    Page<TopicReportDTO> searchReportedTopicsPage(String keyword,Integer pageNum,Integer pageSize,Integer orderType);
    TopicDTO addTopic(String topic_name);
    TopicDTO modifyTopic(TopicModifyParam topicModifyParam);
    void updateTopicHeat();
    Page<TopicHeatResult> getHotList(Integer pageNum,Integer pageSize);
    void updateAllTopicHeat();
}
