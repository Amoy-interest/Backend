package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.UserCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCountRepository extends JpaRepository<UserCount,Integer> {
}
