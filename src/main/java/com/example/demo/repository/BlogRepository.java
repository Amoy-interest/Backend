package com.example.demo.repository;

import com.example.demo.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;


public interface BlogRepository extends JpaRepository<Blog,Integer> {
    Blog findBlogByBlog_id(Integer blog_id);

    @Modifying
    @Transactional
    void deleteByBlog_id(Integer blog_id);
}
