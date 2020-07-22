package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogDao {
    Blog findBlogByBlog_id(Integer blog_id);
    Blog saveBlog(Blog blog);
    void deleteByBlog_id(Integer blog_id);
    List<Blog> getAllBlogs();
    List<Blog> getBlogsByUser_id(Integer user_id);
    Page<Blog> findBlogListByBlog_text(String keyword, Pageable pageable);
    Page<Blog> findBlogListByUser_id(Integer user_id, Pageable pageable);
    Page<Blog> findBlogListByTopic_id(Integer topic_id, Pageable pageable);
    Page<Blog> findReportedBlogsPage(Pageable pageable);
}
