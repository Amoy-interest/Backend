package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserCount;

import java.util.List;

public interface UserCountDao {
    void insert(UserCount userCount);
    void update(UserCount userCount);
    void saveAll(List<UserCount> list);
    UserCount getByUserID(Integer user_id);
}
