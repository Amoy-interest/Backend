package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogHeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/24 18:46
 */
public interface BlogHeatDao {
    void save(BlogHeat blogHeat);

    void saveAll(List<BlogHeat> list);

    Page<Blog> getHotBlogByTopic_id(Integer topic_id, Pageable pageable);

    Page<Blog> getHotBlogByUser_id(Integer user_id, Pageable pageable);

    Page<Blog> getHotBlog(Pageable pageable);
}
