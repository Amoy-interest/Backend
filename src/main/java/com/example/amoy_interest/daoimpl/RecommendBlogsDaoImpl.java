package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.RecommendBlogsDao;
import com.example.amoy_interest.entity.Blog;

import java.util.List;

import com.example.amoy_interest.repository.RecommendBlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendBlogsDaoImpl implements RecommendBlogsDao {
    @Autowired
    private RecommendBlogsRepository recommendBlogsRepository;

    @Override
    public List<Blog> getRecommendBlogsUsingUser_id(Integer user_id) {
        return recommendBlogsRepository.getRecommendBlogsUsingUser_id(user_id);
    }

    @Override
    public void updateRecommendBlogs(Integer user_id, List<Integer> list) {
        recommendBlogsRepository.updateRecommendBlogs(user_id, list);
    }
}
