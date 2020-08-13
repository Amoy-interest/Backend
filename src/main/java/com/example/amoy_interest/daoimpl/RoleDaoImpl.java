package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.RoleDao;
import com.example.amoy_interest.entity.UserRole;
import com.example.amoy_interest.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/28 10:48
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public List<UserRole> getRoleByUsername(String username) {
        return userRoleRepository.getAllByUsername(username);
    }

    @Override
    public boolean insert(UserRole userRole) {
        userRoleRepository.save(userRole);
        return true;
    }
}
