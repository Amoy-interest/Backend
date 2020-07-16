package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
    @Query(value = "from Topic where topic_name = :topic_name")
    Topic getTopicByName(String topic_name);

    @Query(value = "from Topic where report_count > 10 and check_status = 0")
    List<Topic> getReportedTopic();
}
