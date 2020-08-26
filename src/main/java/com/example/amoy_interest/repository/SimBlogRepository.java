package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.SimBlog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;


public interface SimBlogRepository extends JpaRepository<SimBlog, Integer> {
    @Query(value = "select b from SimBlog as sb left join Blog as b on sb.sim_id = b.blog_id where sb.blog_id = :blog_id and b.is_deleted = false and b.check_status <> 2",
            countQuery = "select count(b.blog_id) from SimBlog as sb left join Blog as b on sb.sim_id = b.blog_id where sb.blog_id = :blog_id and b.is_deleted = false and b.check_status <> 2")
    Page<Blog> getSimBlogUsingBlog_id(Integer blog_id, Pageable pageable);
}
