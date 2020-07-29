package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Mok
 * @Date: 2020/7/29 17:33
 */
public interface BlogVoteRepository extends JpaRepository<BlogVote, Integer> {
    @Query(value = "from BlogVote where blog_id = :blog_id and user_id = :user_id")
    BlogVote getByBlog_idAndUser_id(Integer blog_id,Integer user_id);
}
