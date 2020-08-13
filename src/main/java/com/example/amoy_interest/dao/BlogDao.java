package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.ESBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.List;

public interface BlogDao {
    Blog findBlogByBlog_id(Integer blog_id);
    Blog saveBlog(Blog blog);
    void deleteByBlog_id(Integer blog_id);
    List<Blog> getAllBlogs();
    List<Blog> getBlogsByUser_id(Integer user_id);
    Page<Blog> findBlogListByUser_id(Integer user_id, Pageable pageable);
    Page<Blog> findBlogListByTopic_id(Integer topic_id, Pageable pageable);
    Page<Blog> findReportedBlogsPage(Pageable pageable);
    Page<Blog> searchReportedBlogsPage(String keyword,Pageable pageable);
    /**
     *
     * @param pageable
     * @return 按照给定的分页和排序获取博文（范围为所有用户）
     */
    Page<Blog> getAllBlogPage(Pageable pageable);

    /**
     *
     * @param user_id
     * @param pageable
     * @return 按照给定的分页和排序获取关注的博文
     */
    Page<Blog> getFollowBlogPageByUser_id(Integer user_id,Pageable pageable);

    /**
     *
     * @param user_id
     * @param pageable
     * @return 按照给定的分页和排序获取某用户的博文
     */
    Page<Blog> getBlogPageByUser_id(Integer user_id,Pageable pageable);
}
