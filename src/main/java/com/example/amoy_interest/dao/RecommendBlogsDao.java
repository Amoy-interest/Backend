package com.example.amoy_interest.dao;
import java.util.List;
import com.example.amoy_interest.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecommendBlogsDao {
    Page<Blog> getRecommendBlogsUsingUser_id(Integer user_id, Pageable pageable);
    void updateRecommendBlogs(Integer user_id, List<Integer> list);
}
