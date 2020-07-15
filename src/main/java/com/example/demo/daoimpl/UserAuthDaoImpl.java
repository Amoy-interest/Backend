package com.example.demo.daoimpl;

import com.example.demo.dao.UserAuthDao;
import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDaoImpl implements UserAuthDao {
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Override
    public UserAuth findUserByUsername(String username) {
        return userAuthRepository.findUserByUsername(username);
    }

    @Override
    public UserAuth findUserById(Integer user_id) {
        return userAuthRepository.findUserById(user_id);
    }

    @Override
    public UserAuth insert(UserAuth userAuth) {
        return userAuthRepository.saveAndFlush(userAuth);
    }

    @Override
    public void update(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }
}
