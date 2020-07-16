package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.User;

public interface UserDao {
    void insert(User user);
    void update(User user);
    User getById(Integer user_id);
}
