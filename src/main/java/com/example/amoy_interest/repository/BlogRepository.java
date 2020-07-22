package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query(value = "from Blog where blog_id = :blog_id")
    Blog findBlogByBlog_id(Integer blog_id);

    @Query(value = "from Blog where user_id = :user_id")
    List<Blog> findBlogsByUser_id(Integer user_id);

    @Query(value = "from Blog")
    List<Blog> getAllBlogs();

    @Query(value = "SELECT * FROM blog WHERE blog_text like %?1%",
            countQuery = "SELECT count(*) From blog WHERE blog_text like %?1%",
            nativeQuery = true)
    Page<Blog> findListByBlog_textLike(String Blog_text, Pageable pageable);

    @Query(value = "SELECT * FROM blog WHERE topic_id = ?1",
            countQuery = "SELECT count(*) From blog WHERE topic_id = ?1",
            nativeQuery = true)
    Page<Blog> findListByTopic_id(Integer topic_id, Pageable pageable);

    @Query(value = "SELECT * FROM blog WHERE user_id = ?1",
            countQuery = "SELECT count(*) From blog WHERE user_id = ?1",
            nativeQuery = true)
    Page<Blog> findListByUser_id(Integer user_id, Pageable pageable);

    //如何写？
//    @Query(value = "SELECT * FROM blog as b WHERE b.is_deleted = 0 and b.check_status = 0 and (report_count > 10)",
//            countQuery = "SELECT count(*) FROM blog natural join blog_count WHERE is_deleted = 0 and check_status = 0 and report_count > 10",
//            nativeQuery = true)
    @Query(value = "From Blog b where b.check_status = 0 and b.is_deleted = false and b.blogCount.report_count > 10", countQuery = "SELECT count(b.blog_id) From Blog b where b.check_status = 0 and b.is_deleted = false and b.blogCount.report_count > 10")
    Page<Blog> findReportedBlogsPage(Pageable pageable);

    @Query(value = "From Blog b where b.check_status <> 2 and b.is_deleted = false and b.user_id = ?1",
            countQuery = "SELECT count(b.blog_id) from Blog b where b.check_status <> 2 and b.is_deleted = false and b.user_id = ?1")
    Page<Blog> getBlogPageByUser_id(Integer user_id, Pageable pageable);

    //需要优化？
    @Query(value = "SELECT * From blog b where b.user_id = ?1 or b.user_id in (SELECT follow_id from user_follow u where user_id = ?1)",
            countQuery = "SELECT count(*) From blog b where b.user_id = ?1 or b.user_id in (SELECT follow_id from user_follow u where user_id = ?1)",
            nativeQuery = true)
    Page<Blog> getFollowBlogPageByUser_id(Integer user_id, Pageable pageable);

    @Query(value = "FROM Blog b where b.is_deleted = false and b.check_status <> 2",
            countQuery = "SELECT count(b.blog_id) FROM Blog b where b.is_deleted = false and b.check_status <> 2")
    Page<Blog> getAllBlogPage(Pageable pageable);



}
