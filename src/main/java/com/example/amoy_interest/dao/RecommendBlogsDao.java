package com.example.amoy_interest.dao;
import java.util.List;
import com.example.amoy_interest.entity.Blog;

public interface RecommendBlogsDao {
    List<Blog> getRecommendBlogsUsingUser_id(Integer user_id);
    void updateRecommendBlogs(Integer user_id, List<Integer> list);
}
