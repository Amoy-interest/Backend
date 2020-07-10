package com.example.demo.dao;

import com.example.demo.entity.User;

public interface UserDao {
    User findUserByUsername(String username);
    User findUserById(Integer user_id);
}
