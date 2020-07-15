package com.example.demo.daoimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;
import com.example.demo.repository.BlogCommentRepository;
import com.example.demo.repository.BlogCountRepository;
import com.example.demo.repository.BlogImageRepository;
import com.example.demo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.saveAndFlush(blog);
    }

    @Override
    public Blog findBlogByBlog_id(Integer blog_id) {
        return blogRepository.findBlogByBlog_id(blog_id);
    }

    @Override
    public void deleteByBlog_id(Integer blog_id) {
        //只是逻辑删除
        Blog blog = blogRepository.findBlogByBlog_id(blog_id);
        blog.set_deleted(true);
        blogRepository.saveAndFlush(blog);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.getAllBlogs();
    }
}
