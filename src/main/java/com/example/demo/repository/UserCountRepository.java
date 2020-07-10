package com.example.demo.repository;

import com.example.demo.entity.UserCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCountRepository extends JpaRepository<UserCount,Integer> {
}
