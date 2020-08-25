package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.RecommendBlogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecommendBlogsRepository extends JpaRepository<RecommendBlogs, Integer> {
    @Query(value = "select b from RecommendBlogs as rb join Blog as b on rb.blog_id = b.blog_id where rb.user_id = :user_id")
    List<Blog> getRecommendBlogsUsingUser_id(Integer user_id);
}
