package com.example.demo.repository;

import com.example.demo.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface UserFollowRepository extends JpaRepository<UserFollow,Integer> {
}
