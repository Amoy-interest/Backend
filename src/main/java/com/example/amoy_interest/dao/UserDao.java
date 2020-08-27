package com.example.amoy_interest.dao;

import com.example.amoy_interest.dto.UserReportDTO;
import com.example.amoy_interest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    User insert(User user);
    User update(User user);
    User getById(Integer user_id);
    List<User> getReportedUsers();
    Page<UserReportDTO> getReportedUsersPage(Pageable pageable);
    Page<UserReportDTO> searchReportedUsersPage(String keyword, Pageable pageable);
    Page<User> searchUsersPage(String keyword,Pageable pageable);
}
