package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    @Query(value = "from BlogComment where blog_id = :blog_id and comment_level = 1")
    List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id);

    @Query(value = "from BlogComment where comment_id = :comment_id")
    BlogComment findBlogCommentByComment_id(Integer comment_id);

    @Modifying
    @Query(value = "update BlogComment bc set bc.vote_count = bc.vote_count + 1 where bc.comment_id = :comment_id")
    void incrCommentVoteCount(Integer comment_id);

    @Modifying
    @Query(value = "update BlogComment bc set bc.vote_count = bc.vote_count - 1 where bc.comment_id = :comment_id")
    void decrCommentVoteCount(Integer comment_id);

    @Query(value = "SELECT * FROM blog_comment WHERE blog_id = ?1 and comment_level = 1 and is_deleted=false",
            countQuery = "SELECT count(*) From blog_comment WHERE blog_id = ?1 and comment_level = 1 and is_deleted=false",
            nativeQuery = true)
    Page<BlogComment> findLevel1CommentListByBlog_id(Integer blog_id, Pageable pageable);

    @Query(value = "SELECT * FROM blog_comment WHERE root_comment_id = ?1 and is_deleted=false",
            countQuery = "SELECT count(*) From blog_comment WHERE root_comment_id = ?1 and is_deleted=false",
            nativeQuery = true)
    Page<BlogComment> findMultiLevelCommentListByBlog_id(Integer root_comment_id, Pageable pageable);

    @Query(value = "SELECT * From blog_comment where root_comment_id = ?1 limit 1",nativeQuery = true)
    BlogComment findOneByRoot_comment_id(Integer root_comment_id);
}
