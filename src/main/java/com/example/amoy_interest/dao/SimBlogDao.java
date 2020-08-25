package com.example.amoy_interest.dao;
import java.util.List;
import com.example.amoy_interest.entity.Blog;

public interface SimBlogDao {
    List<Blog> getSimBlogUsingBlog_id(Integer blog_id);
}
