package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.TopicHeatDao;
import com.example.amoy_interest.entity.TopicHeat;
import com.example.amoy_interest.repository.TopicHeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TopicHeatDaoImpl implements TopicHeatDao {
    @Autowired
    private TopicHeatRepository topicHeatRepository;

    @Override
    public void saveAll(List<TopicHeat> topicHeatList) {
        topicHeatRepository.saveAll(topicHeatList);
    }

    @Override
    public Page<TopicHeat> findByPage(Pageable pageable) {
        return topicHeatRepository.findAll(pageable);
    }
}
