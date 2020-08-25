package com.example.amoy_interest.repository;
import com.example.amoy_interest.entity.RecommendBlogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import com.example.amoy_interest.entity.Blog;

public interface RecommendBlogsRepository extends JpaRepository<RecommendBlogs, Integer> {
    @Query(value = "select b from RecommendBlogs as rb join Blog as b on rb.blog_id = b.blog_id where rb.user_id = :user_id and rb.recommended = false")
    List<Blog> getRecommendBlogsUsingUser_id(Integer user_id);

    @Modifying
    @Query(value = "update RecommendBlogs rb set rb.recommended = true where rb.user_id = :user_id and rb.blog_id in :list")
    void updateRecommendBlogs(Integer user_id, List<Integer> list);
}
