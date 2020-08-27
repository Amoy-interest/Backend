package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.SimUserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecommendService {
    Page<BlogDTO> getRecommendBlogsUsingUser_id(Integer user_id, Integer pageNum, Integer pageSize);
    Page<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, int limit_count);
    Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, int limit_count);
}
