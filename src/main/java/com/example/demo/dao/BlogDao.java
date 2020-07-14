package com.example.demo.dao;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;

import java.util.List;

public interface BlogDao {
    Blog saveBlog(Blog blog);
    BlogComment saveBlogComment(BlogComment blogComment);
    BlogCount saveBlogCount(BlogCount blogCount);

    Blog findBlogByBlog_id(Integer blog_id);
    void deleteByBlog_id(Integer blog_id);

    List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id);
    BlogCount findBlogCountByBlog_id(Integer blog_id);
    List<BlogImage> findBlogImageByBlog_id(Integer blog_id);

    void incrVoteCount(Integer blog_id);
    void incrCommentVoteCount(Integer comment_id);

    List<Blog> getAllBlogs();
}
