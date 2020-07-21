package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserFollowDao {
    void insert(UserFollow userFollow);
    void delete(UserFollow userFollow);
    Page<UserFollow> findFollowPageByUser_id(Integer user_id,Pageable pageable);
    Page<UserFollow> findFollowPageByFollow_id(Integer follow_id,Pageable pageable);
}
