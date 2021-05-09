package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogHeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Mok
 * @Date: 2020/8/24 18:37
 */
public interface BlogHeatRepository extends JpaRepository<BlogHeat, Integer> {
    @Query(value = "select b From BlogHeat as bh left join TopicBlog as tb left join Blog as b on bh.blog_id = tb.blog_id and tb.blog_id = b.blog_id where tb.topic_id = ?1 and b.is_deleted = false and b.check_status <> 2 order by bh.heat desc",
            countQuery = "SELECT count(b.blog_id) From BlogHeat as bh join TopicBlog as tb join Blog as b on bh.blog_id = tb.blog_id and tb.blog_id = b.blog_id where tb.topic_id = ?1 and b.is_deleted = false and b.check_status <> 2"
    )
    Page<Blog> getHotBlogByTopic_id(Integer topic_id, Pageable pageable);

    @Query(value = "SELECT b from BlogHeat as bh left join Blog b on bh.blog_id = b.blog_id where b.user_id = ?1 and b.is_deleted = false and b.check_status <> 2 order by bh.heat desc",
            countQuery = "SELECT count(b.blog_id) from BlogHeat as bh left join Blog b on bh.blog_id = b.blog_id where b.user_id = ?1 and b.is_deleted = false and b.check_status <> 2 "
    )
    Page<Blog> getHotBlogByUser_id(Integer user_id, Pageable pageable);

    @Query(value = "select b from Blog b left join BlogHeat as bh on b.blog_id = bh.blog_id where b.is_deleted = false and b.check_status <> 2 order by bh.heat desc",
            countQuery ="select count(b.blog_id) from Blog b left join BlogHeat as bh on b.blog_id = bh.blog_id where b.is_deleted = false and b.check_status <> 2")
    Page<Blog> getHotBlog(Pageable pageable);
}
