package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.UserRole;

import java.util.List;

public interface RoleDao {
    List<UserRole> getRoleByUsername(String username);
    boolean insert(UserRole userRole);
}
