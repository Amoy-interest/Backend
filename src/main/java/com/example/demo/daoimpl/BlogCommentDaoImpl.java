package com.example.demo.daoimpl;

import com.example.demo.dao.BlogCommentDao;
import com.example.demo.entity.BlogComment;
import com.example.demo.repository.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void incrCommentVoteCount(Integer comment_id) {
        blogCommentRepository.incrCommentVoteCount(comment_id);
    }

}
