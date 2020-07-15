package com.example.demo.dao;

import com.example.demo.entity.UserAuth;

public interface UserAuthDao {
    UserAuth findUserByUsername(String username);
    UserAuth findUserById(Integer user_id);
    UserAuth insert(UserAuth userAuth);
    void update(UserAuth userAuth);
}
