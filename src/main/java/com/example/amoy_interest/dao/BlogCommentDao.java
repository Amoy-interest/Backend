package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogComment;

import java.util.List;

public interface BlogCommentDao {
    List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id);
    void deleteByComment_id(Integer comment_id);
    BlogComment saveBlogComment(BlogComment blogComment);
    void incrCommentVoteCount(Integer comment_id);
    void decrCommentVoteCount(Integer comment_id);
}
