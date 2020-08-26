package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.SimUserDao;
import com.example.amoy_interest.dto.SimUserDTO;
import com.example.amoy_interest.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimUserDaoImpl implements SimUserDao {
    @Autowired
    private SimUserRepository simUserRepository;

    @Override
    public Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, Pageable pageable){
        return simUserRepository.getSimUserUsingUser_id(my_user_id, user_id, pageable);
    }
}
