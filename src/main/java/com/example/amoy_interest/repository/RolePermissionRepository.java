package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/28 10:47
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer> {
    @Query(value = "from RolePermission where role_name = :role_name")
    List<RolePermission> getAllByRole_name(String role_name);
}
