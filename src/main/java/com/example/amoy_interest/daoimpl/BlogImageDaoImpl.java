package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogImageDao;
import com.example.amoy_interest.entity.BlogImage;
import com.example.amoy_interest.repository.BlogImageRepository;
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

    @Override
    public BlogImage save(BlogImage blogImage) {
        return blogImageRepository.saveAndFlush(blogImage);
    }
}
