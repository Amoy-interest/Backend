package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserBan;

import java.util.Optional;

public interface UserBanDao {
    Optional<UserBan> findUserBanById(Integer user_id);
    void insert(UserBan userBan);
}
