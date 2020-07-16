package com.example.demo.daoimpl;

import com.example.demo.dao.BlogCountDao;
import com.example.demo.entity.BlogCount;
import com.example.demo.repository.BlogCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogCountDaoImpl implements BlogCountDao {
    @Autowired
    private BlogCountRepository blogCountRepository;

    @Override
    public void incrVoteCount(Integer blog_id) {
        blogCountRepository.incrVoteCount(blog_id);
    }

    @Override
    public void decrVoteCount(Integer blog_id) {
        blogCountRepository.decrVoteCount(blog_id);
    }

    @Override
    public BlogCount findBlogCountByBlog_id(Integer blog_id) {
        return blogCountRepository.findBlogCountByBlog_id(blog_id);
    }

    @Override
    public List<BlogCount> findReportedBlogs() {
        return blogCountRepository.findReportedBlogs();
    }

    @Override
    public BlogCount saveBlogCount(BlogCount blogCount) {
        return blogCountRepository.saveAndFlush(blogCount);
    }

}
