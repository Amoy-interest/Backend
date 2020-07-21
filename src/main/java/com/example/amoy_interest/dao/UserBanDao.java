package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserBan;

public interface UserBanDao {
    UserBan findUserBanById(Integer user_id);
    void insert(UserBan userBan);
}
