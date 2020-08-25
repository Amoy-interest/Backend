package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.User;

import java.util.List;

public interface RecommendService {
    List<BlogDTO> getRecommendBlogsUsingUser_id(Integer user_id, int limit_count);
    List<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, int limit_count);
    List<User> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, int limit_count);
}
