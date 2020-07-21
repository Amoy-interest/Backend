package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogCommentDao;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.repository.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogCommentDaoImpl implements BlogCommentDao {
    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Override
    public BlogComment saveBlogComment(BlogComment blogComment) {
        return blogCommentRepository.saveAndFlush(blogComment);
    }

    @Override
    public List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id) {
        return blogCommentRepository.findLevel1CommentByBlog_id(blog_id);
    }

    @Override
    public void deleteByComment_id(Integer comment_id) {
        BlogComment blogComment = blogCommentRepository.findBlogCommentByComment_id(comment_id);
        blogComment.set_deleted(true);
        blogCommentRepository.saveAndFlush(blogComment);
    }

    @Override
    public void incrCommentVoteCount(Integer comment_id) {
        blogCommentRepository.incrCommentVoteCount(comment_id);
    }

    @Override
    public void decrCommentVoteCount(Integer comment_id) {
        blogCommentRepository.decrCommentVoteCount(comment_id);
    }

    @Override
    public Page<BlogComment> findLevel1CommentListByBlog_id(Integer blog_id, Pageable pageable) {
        return blogCommentRepository.findLevel1CommentListByBlog_id(blog_id,pageable);
    }

    @Override
    public Page<BlogComment> findMultiLevelCommentListByComment_id(Integer root_comment_id, Pageable pageable) {
        return blogCommentRepository.findMultiLevelCommentListByBlog_id(root_comment_id, pageable);
    }
}
