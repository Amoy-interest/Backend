package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.SimUserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecommendService {
    Page<BlogDTO> takeRecommendBlogsUsingUser_id(Integer user_id, Integer pageNum, Integer pageSize);
    Page<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, Integer limit_count);
    Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, Integer limit_count);
}
