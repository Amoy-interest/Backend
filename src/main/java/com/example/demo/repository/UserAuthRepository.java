package com.example.demo.repository;

import com.example.demo.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAuthRepository extends JpaRepository<UserAuth,Integer> {
    @Query(value = "from UserAuth where username = :username")
    UserAuth findUserByUsername(String username);
    @Query(value = "from UserAuth where user_id = :user_id")
    UserAuth findUserById(Integer user_id);
}
