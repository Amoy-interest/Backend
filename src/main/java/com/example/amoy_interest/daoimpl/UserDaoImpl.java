package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserDao;
import com.example.amoy_interest.dto.UserReportDTO;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Integer user_id) {
        return userRepository.getOne(user_id);
    }

    @Override
    public List<User> getReportedUsers() {
        return userRepository.getReportedUsers();
    }

    @Override
    public Page<UserReportDTO> getReportedUsersPage(Pageable pageable) {
        return userRepository.findReportedUsersPage(pageable);
    }

    @Override
    public Page<UserReportDTO> searchReportedUsersPage(String keyword, Pageable pageable) {
        return userRepository.searchReportedUsersPage(keyword,pageable);
    }

    @Override
    public Page<User> searchUsersPage(String keyword, Pageable pageable) {
        return userRepository.searchUsersPage(keyword, pageable);
    }
}
