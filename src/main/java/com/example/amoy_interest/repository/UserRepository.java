package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
