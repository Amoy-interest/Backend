package com.example.demo.daoimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.repository.BlogCommentRepository;
import com.example.demo.repository.BlogCountRepository;
import com.example.demo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogCommentRepository blogCommentRepository;
    @Autowired
    private BlogCountRepository blogCountRepository;

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.saveAndFlush(blog);
    }

    @Override
    public BlogComment saveBlogComment(BlogComment blogComment) {
        return blogCommentRepository.saveAndFlush(blogComment);
    }

    @Override
    public BlogCount saveBlogCount(BlogCount blogCount) {
        return blogCountRepository.saveAndFlush(blogCount);
    }

    @Override
    public Blog findBlogByBlog_id(Integer blog_id) {
        return blogRepository.findBlogByBlog_id(blog_id);
    }

    @Override
    public void deleteByBlog_id(Integer blog_id) {
        blogRepository.deleteByBlog_id(blog_id);
    }
}
