package com.example.demo.dao;

import com.example.demo.entity.User;

public interface UserDao {
    void insert(User user);
    void update(User user);
    User getById(Integer user_id);
}
