package com.example.demo.serviceimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogDao blogDao;
    @Autowired
    UserDao userDao;
}
