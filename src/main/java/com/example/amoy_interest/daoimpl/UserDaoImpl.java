package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserDao;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(Integer user_id) {
        return userRepository.getOne(user_id);
    }

    @Override
    public List<User> getReportedUsers() {
        return userRepository.getReportedUsers();
    }
}
