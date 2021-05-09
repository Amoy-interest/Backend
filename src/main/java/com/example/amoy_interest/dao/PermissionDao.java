package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.RolePermission;

import java.util.List;

public interface PermissionDao {
    List<RolePermission> getPermissionByRoleName(String role_name);
}
