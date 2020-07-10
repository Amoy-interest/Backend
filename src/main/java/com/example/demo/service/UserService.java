package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
}
