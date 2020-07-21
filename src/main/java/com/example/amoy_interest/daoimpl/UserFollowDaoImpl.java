package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserFollowDao;
import com.example.amoy_interest.entity.UserFollow;
import com.example.amoy_interest.repository.UserFollowRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

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
    public Page<UserFollow> test(Pageable pageable) {
        return userFollowRepository.findAll(pageable);
    }
}
