package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where credits < 60")
    List<User> getReportedUsers();
    @Query(value = "SELECT * FROM user_info WHERE credits < 60",
            countQuery = "SELECT count(*) From user_info WHERE credits < 60",
            nativeQuery = true)
    Page<User> findReportedUsersPage(Pageable pageable);
    @Query(value = "SELECT * FROM user_info WHERE credits < 60 and nickname = ?1",
            countQuery = "SELECT count(*) From user_info WHERE credits < 60 and nickname = ?1",
            nativeQuery = true)
    Page<User> searchReportedUsersPage(String keyword,Pageable pageable);
}
