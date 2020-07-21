package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogCommentDao {
    List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id);
    void deleteByComment_id(Integer comment_id);
    BlogComment saveBlogComment(BlogComment blogComment);
    void incrCommentVoteCount(Integer comment_id);
    void decrCommentVoteCount(Integer comment_id);
    Page<BlogComment> findLevel1CommentListByBlog_id(Integer blog_id, Pageable pageable);
    Page<BlogComment> findMultiLevelCommentListByComment_id(Integer root_comment_id, Pageable pageable);
}
