package com.example.amoy_interest.dao;

import com.example.amoy_interest.dto.UserBanResult;
import com.example.amoy_interest.dto.UserForbiddenResult;
import com.example.amoy_interest.entity.UserBan;
import com.example.amoy_interest.repository.UserBanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface UserBanDao {
    Optional<UserBan> findUserBanById(Integer user_id);

    void insert(UserBan userBan);

    Page<UserBanResult> getUserBanPage(Pageable pageable, Date time);

    Page<UserBanResult> searchUserBanPage(String keyword, Pageable pageable, Date time);

    Page<UserForbiddenResult> getUserForbidPage(Pageable pageable, Date time);

    Page<UserForbiddenResult> searchUserForbidPage(String keyword, Pageable pageable, Date time);
}
