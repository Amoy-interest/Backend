package com.example.demo.service;

import com.example.demo.dto.BlogDTO;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;

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
