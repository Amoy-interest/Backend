package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "from User where username = :username")
    User findUserByUsername(String username);
    @Query(value = "from User where user_id = :user_id")
    User findUserById(Integer user_id);
}
