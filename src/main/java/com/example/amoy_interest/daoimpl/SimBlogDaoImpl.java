package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.SimBlogDao;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.repository.SimBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public class SimBlogDaoImpl implements SimBlogDao {
    @Autowired
    private SimBlogRepository simBlogRepository;

    @Override
    public Page<Blog> getSimBlogUsingBlog_id(Integer blog_id, Pageable pageable) {
        return simBlogRepository.getSimBlogUsingBlog_id(blog_id, pageable);
    }
}
