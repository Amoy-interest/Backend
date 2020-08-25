package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.SimUserDao;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimUserDaoImpl implements SimUserDao {
    @Autowired
    private SimUserRepository simUserRepository;

    @Override
    public List<User> getSimUserUsingUser_id(Integer user_id){
        return simUserRepository.getSimUserUsingUser_id(user_id);
    }
}
