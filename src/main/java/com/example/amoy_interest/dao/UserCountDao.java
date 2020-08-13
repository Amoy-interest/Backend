package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserCount;

public interface UserCountDao {
    void insert(UserCount userCount);
    void update(UserCount userCount);
    UserCount getByUserID(Integer user_id);
}
