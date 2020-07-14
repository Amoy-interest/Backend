package com.example.demo.daoimpl;

import com.example.demo.dao.BlogImageDao;
import com.example.demo.entity.BlogImage;
import com.example.demo.repository.BlogImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogImageDaoImpl implements BlogImageDao {
    @Autowired
    private BlogImageRepository blogImageRepository;

    @Override
    public List<BlogImage> findBlogImageByBlog_id(Integer blog_id) {
        return blogImageRepository.findBlogImageByBlog_id(blog_id);
    }
}
