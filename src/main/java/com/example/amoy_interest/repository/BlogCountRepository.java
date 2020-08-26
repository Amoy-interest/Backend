package com.example.amoy_interest.repository;

import com.example.amoy_interest.dto.BlogHeatParam;
import com.example.amoy_interest.entity.BlogCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface BlogCountRepository extends JpaRepository<BlogCount,Integer> {
    @Query(value = "from BlogCount where blog_id = :blog_id")
    BlogCount findBlogCountByBlog_id(Integer blog_id);

    @Query(value = "select new com.example.amoy_interest.dto.BlogHeatParam(b.blog_id,bc.forward_count,bc.vote_count,bc.comment_count,b.blog_time) from Blog b left join BlogCount bc on b.blog_id = bc.blog_id where b.is_deleted = false and b.check_status <> 2")
    List<BlogHeatParam> getAllBlogHeatParam();

    @Query(value = "select new com.example.amoy_interest.dto.BlogHeatParam(b.blog_id,bc.forward_count,bc.vote_count,bc.comment_count,b.blog_time) from Blog b left join BlogCount bc on b.blog_id = bc.blog_id where b.is_deleted = false and b.check_status <> 2 and b.blog_time >= ?1")
    List<BlogHeatParam> getBlogHeatParamAfterTime(Date time);

    @Query(value = "from BlogCount where report_count >= 10")
    List<BlogCount> findReportedBlogs();

    @Modifying
    @Query(value = "update BlogCount bc set bc.vote_count = bc.vote_count + 1 where bc.blog_id = :blog_id")
    void incrVoteCount(Integer blog_id);

    @Modifying
    @Query(value = "update BlogCount bc set bc.vote_count = bc.vote_count - 1 where bc.blog_id = :blog_id")
    void decrVoteCount(Integer blog_id);
}
