package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.entity.UserReport;
import com.example.amoy_interest.entity.UserReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:44
 */
public interface UserReportRepository extends JpaRepository<UserReport, UserReportPK> {
    @Query(value = "from UserReport where reporter_id = :reporter_id and user_id = :user_id")
    UserReport getByUser_idAndReporter_id(Integer user_id, Integer reporter_id);
}
