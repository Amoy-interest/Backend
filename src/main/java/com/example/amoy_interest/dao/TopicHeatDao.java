package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.TopicHeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicHeatDao {
    void saveAll(List<TopicHeat> topicHeatList);
    Page<TopicHeat> findByPage(Pageable pageable);
    void deleteAll();
}
