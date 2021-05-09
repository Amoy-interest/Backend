package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.PermissionDao;
import com.example.amoy_interest.entity.RolePermission;
import com.example.amoy_interest.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/28 10:49
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<RolePermission> getPermissionByRoleName(String role_name) {
        return rolePermissionRepository.getAllByRole_name(role_name);
    }
}
