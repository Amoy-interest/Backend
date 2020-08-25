package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.SimUser;
import com.example.amoy_interest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SimUserRepository extends JpaRepository<SimUser, Integer> {
    @Query(value = "select u from SimUser as su left join User as u on su.sim_id = u.user_id where su.user_id = :user_id")
    List<User> getSimUserUsingUser_id(Integer user_id);
}
