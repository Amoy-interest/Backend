package com.example.amoy_interest.dao;
import com.example.amoy_interest.entity.User;

import java.util.List;

public interface SimUserDao {
    List<User> getSimUserUsingUser_id(Integer user_id);
}

