package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    void insert(User user);
    void update(User user);
    User getById(Integer user_id);
    List<User> getReportedUsers();
    Page<User> getReportedUsersPage(Pageable pageable);
}
