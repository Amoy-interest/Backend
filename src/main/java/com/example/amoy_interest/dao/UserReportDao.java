package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.entity.UserReport;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:48
 */
public interface UserReportDao {
    void save(UserReport userReport);
    void saveAll(List<UserReport> list);
    UserReport getByUserIdAndReporterId(Integer user_id,Integer reporter_id);
}
