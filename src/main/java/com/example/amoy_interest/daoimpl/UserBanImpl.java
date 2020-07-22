package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserBanDao;
import com.example.amoy_interest.entity.UserBan;
import com.example.amoy_interest.repository.UserBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserBanImpl implements UserBanDao {
    @Autowired
    UserBanRepository userBanRepository;

    @Override
    public Optional<UserBan> findUserBanById(Integer user_id) {
        return userBanRepository.findById(user_id);
    }

    @Override
    public void insert(UserBan userBan) {
        userBanRepository.save(userBan);
    }
}
