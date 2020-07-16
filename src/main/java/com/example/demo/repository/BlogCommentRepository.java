package com.example.demo.repository;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
}
