package com.example.demo.dao;

import com.example.demo.entity.BlogComment;

import java.util.List;

public interface BlogCommentDao {
    List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id);
    BlogComment saveBlogComment(BlogComment blogComment);
    void incrCommentVoteCount(Integer comment_id);
}