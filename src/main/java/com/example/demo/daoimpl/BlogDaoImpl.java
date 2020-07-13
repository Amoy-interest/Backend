package com.example.demo.daoimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.repository.BlogCommentRepository;
import com.example.demo.repository.BlogCountRepository;
import com.example.demo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BlogDaoImpl implements BlogDao {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    BlogCommentRepository blogCommentRepository;
    @Autowired
    BlogCountRepository blogCountRepository;
}
