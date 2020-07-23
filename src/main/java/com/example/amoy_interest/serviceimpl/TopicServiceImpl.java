package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private BlogDao blogDao;

    @Override
    public Integer getTopic_idByName(String topic_name) {
        return topicDao.getTopicByName(topic_name).getTopic_id();
    }

    @Override
    public TopicDTO getTopicDTOByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        return new TopicDTO(topic);
    }

    @Override
    public boolean addTopic(String topic_name) {
        if(topicDao.getTopicByName(topic_name) == null) {
            Topic topic = new Topic(topic_name,new Date(),0,0,null,null,null);
            topicDao.insert(topic);
        }
        return true;
    }

    @Override
    public boolean modifyTopicIntro(TopicIntroDTO topicIntroDTO) {
        Topic topic = topicDao.getTopicByName(topicIntroDTO.getTopic_name());
        if(topic == null) {
            return false;
        }
        topic.setTopic_intro(topicIntroDTO.getTopic_intro());
        topicDao.update(topic);
        return true;
    }

    @Override
    public boolean modifyTopicLogo(TopicLogoDTO topicLogoDTO) {
        Topic topic = topicDao.getTopicByName(topicLogoDTO.getTopic_name());
        if(topic == null) {
            return false;
        }
        topic.setLogo_path(topicLogoDTO.getLogo_path());
        topicDao.update(topic);
        return true;
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
    public boolean checkReportedTopic(TopicCheckDTO topicCheckDTO) {
        Topic topic = topicDao.getTopicByName(topicCheckDTO.getTopic_name());
        topic.setCheck_status(topicCheckDTO.getCheck_status());
        topicDao.update(topic);
        return true;
    }

    @Override
    public boolean reportTopicByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        topic.setReport_count(topic.getReport_count()+1);
        topicDao.update(topic);
        return true;
    }

    @Override
    public Page<TopicReportDTO> getReportedTopicsPage(Integer pageNum,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Topic> topicPage = topicDao.getReportedTopicPage(pageable);
        List<Topic> topicList = topicPage.getContent();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic: topicList) {
            topicReportDTOList.add(new TopicReportDTO(topic));
        }
        return new PageImpl<>(topicReportDTOList,topicPage.getPageable(),topicPage.getTotalElements());
    }

    @Override
    public Page<TopicReportDTO> searchReportedTopicsPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Topic> topicPage = topicDao.searchReportedTopicPage(keyword,pageable);
        List<Topic> topicList = topicPage.getContent();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic: topicList) {
            topicReportDTOList.add(new TopicReportDTO(topic));
        }
        return new PageImpl<>(topicReportDTOList,topicPage.getPageable(),topicPage.getTotalElements());
    }
}
