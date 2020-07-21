package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.UserFollow;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow,Integer> {
}
