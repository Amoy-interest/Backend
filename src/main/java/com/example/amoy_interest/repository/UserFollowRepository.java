package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.UserFollow;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow,Integer> {
    @Query(value = "SELECT * FROM user_follow WHERE user_id = ?1",
            countQuery = "SELECT count(*) From user_follow WHERE user_id = ?1",
            nativeQuery = true)
    Page<UserFollow> findFollowByUser_id(Integer user_id, Pageable pageable);

    @Query(value = "SELECT * FROM user_follow WHERE follow_id = ?1",
            countQuery = "SELECT count(*) From user_follow WHERE follow_id = ?1",
            nativeQuery = true)
    Page<UserFollow> findFollowByFollow_id(Integer follow_id, Pageable pageable);
}
