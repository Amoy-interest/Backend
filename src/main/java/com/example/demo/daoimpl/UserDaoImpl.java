package com.example.demo.daoimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserFollow;
import com.example.demo.repository.UserCountRepository;
import com.example.demo.repository.UserFollowRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCountRepository userCountRepository;
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserById(Integer user_id) {
        return userRepository.findUserById(user_id);
    }

    @Override
    public User register(RegisterDTO registerDTO) {
//        User
        return null;
    }
}
