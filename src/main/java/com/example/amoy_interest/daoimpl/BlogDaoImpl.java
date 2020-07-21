package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.repository.BlogRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<Blog> getBlogsByUser_id(Integer user_id) {
        return blogRepository.findBlogsByUser_id(user_id);
    }

    @Override
    public Page<Blog> findBlogListByBlog_text(String keyword, Pageable pageable) {
        return blogRepository.findListByBlog_textLike(keyword,pageable);
    }

    @Override
    public Page<Blog> findBlogListByUser_id(Integer user_id, Pageable pageable) {
        return blogRepository.findListByUser_id(user_id,pageable);
    }

    @Override
    public Page<Blog> findBlogListByTopic_id(Integer topic_id, Pageable pageable) {
        return blogRepository.findListByTopic_id(topic_id,pageable);
    }
}
