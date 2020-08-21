package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserReportDao;
import com.example.amoy_interest.entity.UserReport;
import com.example.amoy_interest.repository.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:53
 */
@Repository
public class UserReportDaoImpl implements UserReportDao {
    @Autowired
    private UserReportRepository userReportRepository;

    @Override
    public void save(UserReport userReport) {
        userReportRepository.save(userReport);
    }

    @Override
    public void saveAll(List<UserReport> list) {
        userReportRepository.saveAll(list);
    }

    @Override
    public UserReport getByUserIdAndReporterId(Integer user_id, Integer reporter_id) {
        return userReportRepository.getByUser_idAndReporter_id(user_id, reporter_id);
    }
}
