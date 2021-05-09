package com.example.amoy_interest.repository;

import com.example.amoy_interest.dto.UserBanResult;
import com.example.amoy_interest.dto.UserForbiddenResult;
import com.example.amoy_interest.entity.UserBan;
import com.example.amoy_interest.entity.UserCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserBanRepository extends JpaRepository<UserBan, Integer> {
    @Query(value = "select new com.example.amoy_interest.dto.UserBanResult(ub.user_id,u.nickname,ub.ban_time) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_ban = 1 and ub.ban_time > :time",
            countQuery = "select count(ub.user_id) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_ban = 1 and ub.ban_time > :time")
    Page<UserBanResult> getUserBanPage(Pageable pageable, Date time);

    @Query(value = "select new com.example.amoy_interest.dto.UserForbiddenResult(ub.user_id,u.nickname,ub.forbidden_time) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_forbidden = 1 and ub.forbidden_time > :time",
            countQuery = "select count(ub.user_id) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_forbidden = 1 and ub.forbidden_time > :time")
    Page<UserForbiddenResult> getUserForbidPage(Pageable pageable, Date time);

    @Query(value = "select new com.example.amoy_interest.dto.UserBanResult(ub.user_id,u.nickname,ub.ban_time) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_ban = 1 and ub.ban_time > :time and u.nickname like %:keyword%",
            countQuery = "select count(ub.user_id) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_ban = 1 and ub.ban_time > :time and u.nickname like %:keyword%")
    Page<UserBanResult> searchUserBanPage(String keyword, Pageable pageable, Date time);

    @Query(value = "select new com.example.amoy_interest.dto.UserForbiddenResult(ub.user_id,u.nickname,ub.forbidden_time) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_forbidden = 1 and ub.forbidden_time > :time and u.nickname like %:keyword%",
            countQuery = "select count(ub.user_id) from UserBan ub left join User u on ub.user_id = u.user_id left join UserAuth ua on ub.user_id = ua.user_id where ua.is_forbidden = 1 and ub.forbidden_time > :time and u.nickname like %:keyword%")
    Page<UserForbiddenResult> searchUserForbidPage(String keyword,Pageable pageable, Date time);
}
