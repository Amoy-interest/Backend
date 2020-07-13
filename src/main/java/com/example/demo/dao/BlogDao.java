package com.example.demo.dao;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;

public interface BlogDao {
    Blog saveBlog(Blog blog);
    BlogComment saveBlogComment(BlogComment blogComment);
    BlogCount saveBlogCount(BlogCount blogCount);

    Blog findBlogByBlog_id(Integer blog_id);
    void deleteByBlog_id(Integer blog_id);
}
