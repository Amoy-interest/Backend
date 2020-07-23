package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserFollowDao;
import com.example.amoy_interest.entity.UserFollow;
import com.example.amoy_interest.repository.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

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

    @Override
    public Page<UserFollow> findFollowPageByUser_id(Integer user_id, Pageable pageable) {
        return userFollowRepository.findFollowByUser_id(user_id,pageable);
    }

    @Override
    public Page<UserFollow> findFollowPageByFollow_id(Integer follow_id, Pageable pageable) {
        return userFollowRepository.findFollowByFollow_id(follow_id,pageable);
    }

    @Override
    public Optional<UserFollow> findByUser_idAndFollow_id(Integer user_id, Integer follow_id) {
        return userFollowRepository.findByUser_idAndFollow_id(user_id, follow_id);
    }
}
