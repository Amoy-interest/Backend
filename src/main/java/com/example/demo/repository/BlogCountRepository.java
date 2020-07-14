package com.example.demo.repository;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlogCountRepository extends JpaRepository<BlogCount,Integer> {
    @Query(value = "from BlogCount where blog_id = :blog_id")
    BlogCount findBlogCountByBlog_id(Integer blog_id);

    @Modifying
    @Query(value = "update BlogCount bc set bc.vote_count = bc.vote_count + 1 where bc.blog_id = :blog_id")
    void incrVoteCount(Integer blog_id);
}
