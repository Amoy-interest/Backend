package com.example.demo.serviceimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;

public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogDao blogDao;
}
