package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.dao.TopicHeatDao;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.entity.TopicHeat;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.utils.HotRank;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private TopicHeatDao topicHeatDao;
//    @Autowired
//    private BlogDao blogDao;

    @Override
    public Integer getTopic_idByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        if(topic == null)
            return null;
        else
            return topic.getTopic_id();
    }



    @Override
    public TopicDTO getTopicDTOByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        return new TopicDTO(topic);
    }

    @Override
    public TopicDTO addTopic(String topic_name) {
        if(topicDao.getTopicByName(topic_name) == null) {
            Topic topic = new Topic(topic_name,new Date(),0,0,null,null,null);
            topic.setLogo_path("https://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/common/topic_logo.jpg");
            topic = topicDao.insert(topic);
            return new TopicDTO(topic);
        }
        return null;
    }

    @Override
    public TopicDTO modifyTopicIntro(TopicIntroDTO topicIntroDTO) {
        Topic topic = topicDao.getTopicByName(topicIntroDTO.getTopic_name());
        if(topic == null) {
            return null;
        }
        topic.setTopic_intro(topicIntroDTO.getTopic_intro());
        topicDao.update(topic);
        return new TopicDTO(topic);
    }

    @Override
    public TopicDTO modifyTopicLogo(TopicLogoDTO topicLogoDTO) {
        Topic topic = topicDao.getTopicByName(topicLogoDTO.getTopic_name());
        if(topic == null) {
            return null;
        }
        topic.setLogo_path(topicLogoDTO.getLogo_path());
        topicDao.update(topic);
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
    public boolean checkReportedTopic(TopicCheckDTO topicCheckDTO) {
        Topic topic = topicDao.getTopicByName(topicCheckDTO.getTopic_name());
        topic.setCheck_status(topicCheckDTO.getCheck_status());
        topicDao.update(topic);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean reportTopicByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        topic.setReport_count(topic.getReport_count()+1);
        topicDao.update(topic);
        return true;
    }

    @Override
    public Page<TopicReportDTO> getReportedTopicsPage(Integer pageNum,Integer pageSize,Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"topic_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
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
    public Page<TopicReportDTO> searchReportedTopicsPage(String keyword, Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"topic_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Topic> topicPage = topicDao.searchReportedTopicPage(keyword,pageable);
        List<Topic> topicList = topicPage.getContent();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic: topicList) {
            topicReportDTOList.add(new TopicReportDTO(topic));
        }
        return new PageImpl<>(topicReportDTOList,topicPage.getPageable(),topicPage.getTotalElements());
    }

    @Override
    @Transactional
    public void updateTopicHeat() {
        List<TopicHeatParam> topicHeatParamList = topicDao.getAllTopicCount();
        List<TopicHeat> topicHeatList = new ArrayList<>();
        for(TopicHeatParam topicHeatParam:topicHeatParamList) {
            Integer ups = topicHeatParam.getForward_count()*30 + topicHeatParam.getVote_count()*10 + topicHeatParam.getComment_count()*1;
            int score = (int)HotRank.getHotVal(ups,0,topicHeatParam.getTopic_time());
            TopicHeat topicHeat = new TopicHeat(topicHeatParam.getTopic_id(),score);
            topicHeatList.add(topicHeat);
        }
        topicHeatDao.saveAll(topicHeatList);
    }

    @Override
    public Page<TopicHeatResult> getHotList(Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"heat");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<TopicHeat> topicHeatPage = topicHeatDao.findByPage(pageable);
        List<TopicHeat> topicHeatList = topicHeatPage.getContent();
        List<TopicHeatResult> topicHeatResultList = new ArrayList<>();
        for(TopicHeat topicHeat:topicHeatList) {
            Topic topic = topicDao.getTopicById(topicHeat.getTopic_id());
            TopicHeatResult topicHeatResult = new TopicHeatResult(topic.getTopic_name(),topicHeat.getHeat());
            topicHeatResultList.add(topicHeatResult);
        }
        return new PageImpl<>(topicHeatResultList,topicHeatPage.getPageable(),topicHeatPage.getTotalElements());
    }
}
