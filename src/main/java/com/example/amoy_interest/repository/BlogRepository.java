package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog,Integer> {
    @Query(value = "from Blog where blog_id = :blog_id")
    Blog findBlogByBlog_id(Integer blog_id);

    @Query(value = "from Blog where user_id = :user_id")
    List<Blog> findBlogsByUser_id(Integer user_id);

    @Query(value = "from Blog")
    List<Blog> getAllBlogs();

    @Query(value = "SELECT * FROM blog WHERE blog_text like %?1%",
    countQuery = "SELECT count(*) From blog WHERE blog_text like %?1%",
    nativeQuery = true)
    Page<Blog> findListByBlog_textLike(String Blog_text,Pageable pageable);

    @Query(value = "SELECT * FROM blog WHERE topic_id = ?1",
            countQuery = "SELECT count(*) From blog WHERE topic_id = ?1",
            nativeQuery = true)
    Page<Blog> findListByTopic_id(Integer topic_id,Pageable pageable);

    @Query(value = "SELECT * FROM blog WHERE user_id = ?1",
            countQuery = "SELECT count(*) From blog WHERE user_id = ?1",
            nativeQuery = true)
    Page<Blog> findListByUser_id(Integer user_id,Pageable pageable);
//    Page<Blog> findByBlogTextContaining(String blog_text,Pageable pageable);
//    @Query(value = "from Blog where blog_text like CONCAT('%',:keyword,'%')")
//    Page<Blog> findBlogListByBlog_text(String keyword, Pageable pageable);
}
