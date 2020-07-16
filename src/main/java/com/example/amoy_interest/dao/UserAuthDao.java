package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserAuth;

public interface UserAuthDao {
    UserAuth findUserByUsername(String username);
    UserAuth findUserById(Integer user_id);
    UserAuth insert(UserAuth userAuth);
    void update(UserAuth userAuth);
}
