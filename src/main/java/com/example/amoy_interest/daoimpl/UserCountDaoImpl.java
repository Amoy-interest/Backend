package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.UserCountDao;
import com.example.amoy_interest.entity.UserCount;
import com.example.amoy_interest.repository.UserCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCountDaoImpl implements UserCountDao {
    @Autowired
    UserCountRepository userCountRepository;

    @Override
    public void insert(UserCount userCount) {
        userCountRepository.save(userCount);
    }

    @Override
    public void update(UserCount userCount) {
        userCountRepository.save(userCount);
    }

    @Override
    public UserCount getByUserID(Integer user_id) {
        return userCountRepository.getOne(user_id);
    }

    @Override
    public void saveAll(List<UserCount> list) {
        userCountRepository.saveAll(list);
    }
}
