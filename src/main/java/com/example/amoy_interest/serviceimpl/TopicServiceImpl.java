package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.dto.TopicCheckDTO;
import com.example.amoy_interest.dto.TopicDTO;
import com.example.amoy_interest.dto.TopicReportDTO;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicDao topicDao;

    @Override
    public TopicDTO getTopicByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        return new TopicDTO(topic);
    }

    @Override
    public List<TopicReportDTO> getReportedTopics() {
        List<Topic> topicList = topicDao.getReportedTopic();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic:topicList) {
            TopicReportDTO topicReportDTO = new TopicReportDTO(topic.getTopic_name(),topic.getTopic_time(),topic.getReport_count());
            topicReportDTOList.add(topicReportDTO);
        }
        return topicReportDTOList;
    }

    @Override
    public boolean CheckReportedTopic(TopicCheckDTO topicCheckDTO) {
        Topic topic = topicDao.getTopicByName(topicCheckDTO.getTopic_name());
        topic.setCheck_status(topicCheckDTO.getCheck_status());
        topicDao.update(topic);
        return true;
    }
}
