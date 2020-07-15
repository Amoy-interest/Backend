package com.example.demo.daoimpl;

import com.example.demo.dao.UserCountDao;
import com.example.demo.entity.UserCount;
import com.example.demo.repository.UserCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
