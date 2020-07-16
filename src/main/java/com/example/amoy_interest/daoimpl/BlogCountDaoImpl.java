package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.repository.BlogCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlogCountDaoImpl implements BlogCountDao {
    @Autowired
    private BlogCountRepository blogCountRepository;

    @Override
    public void incrVoteCount(Integer blog_id) {
        blogCountRepository.incrVoteCount(blog_id);
    }

    @Override
    public BlogCount findBlogCountByBlog_id(Integer blog_id) {
        return blogCountRepository.findBlogCountByBlog_id(blog_id);
    }

    @Override
    public BlogCount saveBlogCount(BlogCount blogCount) {
        return blogCountRepository.saveAndFlush(blogCount);
    }

}
