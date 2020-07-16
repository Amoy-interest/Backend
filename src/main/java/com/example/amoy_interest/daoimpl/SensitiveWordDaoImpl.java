package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.SensitiveWordDao;
import com.example.amoy_interest.repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SensitiveWordDaoImpl implements SensitiveWordDao {
    @Autowired
    private SensitiveWordRepository sensitiveWordRepository;
}
