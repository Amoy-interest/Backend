package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;

import java.util.List;

public interface BlogService {
    Blog addBlog(Blog blog);
    Blog updateBlog(Blog blog);

    Blog findBlogByBlog_id(Integer blog_id);
    void deleteByBlog_id(Integer blog_id);

    void incrVoteCount(Integer blog_id);
    void incrCommentVoteCount(Integer comment_id);
    void decrVoteCount(Integer blog_id);
    void decrCommentVoteCount(Integer comment_id);


    BlogComment addBlogComment(BlogComment blogComment);
    void deleteCommentByComment_id(Integer comment_id);

    BlogDTO getSimpleBlogDetail(Integer blog_id);
    BlogDTO getAllBlogDetail(Integer blog_id);

    List<Blog> getAllBlogs();
    List<BlogCount> getAllReportedBlogs();
}
