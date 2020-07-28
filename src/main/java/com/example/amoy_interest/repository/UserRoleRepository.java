package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/28 10:47
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    List<UserRole> getAllByUsername(String username);
}
