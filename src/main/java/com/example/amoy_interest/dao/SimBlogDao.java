package com.example.amoy_interest.dao;
import java.util.List;
import com.example.amoy_interest.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SimBlogDao {
    Page<Blog> getSimBlogUsingBlog_id(Integer blog_id, Pageable pageable);
}
