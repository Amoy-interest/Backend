package com.example.demo.daoimpl;

import com.example.demo.dao.UserFollowDao;
import com.example.demo.entity.UserFollow;
import com.example.demo.repository.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserFollowDaoImpl implements UserFollowDao {
    @Autowired
    UserFollowRepository userFollowRepository;

    @Override
    public void insert(UserFollow userFollow) {
        userFollowRepository.save(userFollow);
    }

    @Override
    public void delete(UserFollow userFollow) {
        userFollowRepository.delete(userFollow);
    }
}
