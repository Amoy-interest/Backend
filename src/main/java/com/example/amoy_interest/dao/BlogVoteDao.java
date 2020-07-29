package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.BlogVote;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/29 17:27
 */
public interface BlogVoteDao {
    BlogVote save(BlogVote blogVote);
    List<BlogVote> saveAll(List<BlogVote> list);
    BlogVote getByBlogIdAndUserId(Integer blog_id,Integer user_id);
}
