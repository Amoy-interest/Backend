package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserFollowDao;
import com.example.amoy_interest.entity.UserFollow;
import com.example.amoy_interest.repository.UserFollowRepository;
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
