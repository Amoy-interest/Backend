package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog,Integer> {
    @Query(value = "from Blog where blog_id = :blog_id")
    Blog findBlogByBlog_id(Integer blog_id);

    @Query(value = "from Blog")
    List<Blog> getAllBlogs();
}
