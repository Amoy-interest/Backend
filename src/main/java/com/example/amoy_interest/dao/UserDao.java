package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    User insert(User user);
    User update(User user);
    User getById(Integer user_id);
    List<User> getReportedUsers();
    Page<User> getReportedUsersPage(Pageable pageable);
    Page<User> searchReportedUsersPage(String keyword,Pageable pageable);
    Page<User> searchUsersPage(String keyword,Pageable pageable);
}
