package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogVoteDao;
import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.repository.BlogVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/29 17:33
 */
@Repository
public class BlogVoteDaoImpl implements BlogVoteDao {
    @Autowired
    private BlogVoteRepository blogVoteRepository;
    @Override
    public BlogVote save(BlogVote blogVote) {
        return blogVoteRepository.save(blogVote);
    }

    @Override
    public List<BlogVote> saveAll(List<BlogVote> list) {
        return blogVoteRepository.saveAll(list);
    }

    @Override
    public BlogVote getByBlogIdAndUserId(Integer blog_id, Integer user_id) {
        return blogVoteRepository.getByBlog_idAndUser_id(blog_id, user_id);
    }
}
