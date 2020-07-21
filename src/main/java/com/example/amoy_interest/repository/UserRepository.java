package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where credits < 60")
    List<User> getReportedUsers();

}
