package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.entity.BlogReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:44
 */
public interface BlogReportRepository extends JpaRepository<BlogReport, BlogReportPK> {
    @Query(value = "from BlogReport where blog_id = :blog_id and user_id = :user_id")
    BlogReport getByBlog_idAndUser_id(Integer blog_id, Integer user_id);
}
