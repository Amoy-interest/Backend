package com.example.demo.repository;

import com.example.demo.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
    @Query(value = "from Topic where topic_name = :topic_name")
    Topic getTopicByName(String topic_name);
}
