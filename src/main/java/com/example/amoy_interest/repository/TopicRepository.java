package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
    @Query(value = "from Topic where topic_name = :topic_name")
    Topic getTopicByName(String topic_name);

    @Query(value = "from Topic where report_count >= 10 and check_status = 0")
    List<Topic> getReportedTopic();

    @Query(value = "SELECT * FROM topic WHERE report_count >= 10 and check_status = 0",
            countQuery = "SELECT count(*) FROM topic WHERE report_count >= 10 and check_status = 0",
            nativeQuery = true)
    Page<Topic> getReportedTopicPage(Pageable pageable);

    @Query(value = "SELECT * FROM topic WHERE report_count >= 10 and check_status = 0 and topic_name like %?1%",
            countQuery = "SELECT count(*) FROM topic WHERE report_count >= 10 and check_status = 0 and topic_name like %?1%",
            nativeQuery = true)
    Page<Topic> searchReportedTopicPage(String keyword,Pageable pageable);

}
