package com.example.demo.daoimpl;

import com.example.demo.dao.SensitiveWordDao;
import com.example.demo.repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SensitiveWordDaoImpl implements SensitiveWordDao {
    @Autowired
    SensitiveWordRepository sensitiveWordRepository;
}
