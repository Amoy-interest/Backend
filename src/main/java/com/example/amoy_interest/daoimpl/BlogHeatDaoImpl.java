package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogHeatDao;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogHeat;
import com.example.amoy_interest.repository.BlogHeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/24 18:47
 */
@Repository
public class BlogHeatDaoImpl implements BlogHeatDao {
    @Autowired
    private BlogHeatRepository blogHeatRepository;

    @Override
    public void save(BlogHeat blogHeat) {
        blogHeatRepository.save(blogHeat);
    }

    @Override
    public void saveAll(List<BlogHeat> list) {
        blogHeatRepository.saveAll(list);
    }

    @Override
    public Page<Blog> getHotBlogByTopic_id(Integer topic_id, Pageable pageable) {
//        return null;
        return blogHeatRepository.getHotBlogByTopic_id(topic_id, pageable);
    }

    @Override
    public Page<Blog> getHotBlogByUser_id(Integer user_id, Pageable pageable) {
        return blogHeatRepository.getHotBlogByUser_id(user_id, pageable);
    }

    @Override
    public Page<Blog> getHotBlog(Pageable pageable) {
//        return null;
        return blogHeatRepository.getHotBlog(pageable);
    }
}
