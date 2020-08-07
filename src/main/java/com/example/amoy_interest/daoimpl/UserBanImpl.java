package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserBanDao;
import com.example.amoy_interest.dto.UserBanResult;
import com.example.amoy_interest.dto.UserForbiddenResult;
import com.example.amoy_interest.entity.UserBan;
import com.example.amoy_interest.repository.UserBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public class UserBanImpl implements UserBanDao {
    @Autowired
    UserBanRepository userBanRepository;

    @Override
    public Optional<UserBan> findUserBanById(Integer user_id) {
        return userBanRepository.findById(user_id);
    }

    @Override
    public void insert(UserBan userBan) {
        userBanRepository.save(userBan);
    }

    @Override
    public Page<UserBanResult> getUserBanPage(Pageable pageable, Date time) {
        return userBanRepository.getUserBanPage(pageable,time);
    }

    @Override
    public Page<UserBanResult> searchUserBanPage(String keyword, Pageable pageable, Date time) {
        return userBanRepository.searchUserBanPage(keyword, pageable, time);
    }

    @Override
    public Page<UserForbiddenResult> getUserForbidPage(Pageable pageable, Date time) {
        return userBanRepository.getUserForbidPage(pageable, time);
    }

    @Override
    public Page<UserForbiddenResult> searchUserForbidPage(String keyword, Pageable pageable, Date time) {
        return userBanRepository.searchUserForbidPage(keyword, pageable, time);
    }
}
