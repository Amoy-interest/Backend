package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.entity.BlogCount;
//import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    BlogDTO addBlog(BlogAddDTO blogContentDTO);
    BlogDTO updateBlog(BlogPutDTO blogPutDTO);

    Blog findBlogByBlog_id(Integer blog_id);
    void deleteByBlog_id(Integer blog_id);

    void incrVoteCount(Integer blog_id);
    void incrCommentVoteCount(Integer comment_id);
    void decrVoteCount(Integer blog_id);
    void decrCommentVoteCount(Integer comment_id);


    BlogComment addBlogComment(BlogComment blogComment);
    void deleteCommentByComment_id(Integer comment_id);

    BlogDTO getSimpleBlogDetail(Integer blog_id);
    BlogDTO getAllBlogDetail(Integer blog_id);

    List<Blog> getAllBlogs();
    List<BlogCount> getAllReportedBlogs();

    List<BlogDTO> getBlogsByUser_id(Integer user_id);
    List<BlogDTO> getRecommendBlogsByUser_id(Integer user_id);
    List<BlogDTO> getFollowBlogsByUser_id(Integer user_id);

    boolean reportBlogByBlog_id(Integer blog_id);
    boolean checkReportedBlog(BlogCheckDTO blogCheckDTO);
    Page<BlogDTO> getSearchListByBlog_text(String keyword, Integer pageNum, Integer pageSize);
    Page<BlogDTO> getListByUser_id(Integer user_id,Integer pageNum,Integer pageSize);
    Page<BlogDTO> getListByTopic_id(Integer topic_id,Integer pageNum,Integer pageSize);

    Page<BlogCommentLevel1DTO> getLevel1CommentPage(Integer blog_id,Integer pageNum,Integer pageSize);
    Page<BlogCommentMultiLevelDTO> getMultiLevelCommentPage(Integer root_comment_id,Integer pageNum,Integer pageSize);
    Page<BlogDTO> getReportedBlogsPage(Integer pageNum,Integer pageSize);
    Page<BlogDTO> searchReportedBlogsPage(String keyword,Integer pageNum,Integer pageSize);

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return 分页返回最新博文
     */
    Page<BlogDTO> getAllBlogPageOrderByTime(Integer pageNum,Integer pageSize);

    /**
     *
     * @param user_id
     * @param pageNum
     * @param pageSize
     * @return 分页返回关注和自己的最新博文
     */
    Page<BlogDTO> getFollowBlogPageByUser_idOrderByTime(Integer user_id,Integer pageNum,Integer pageSize);

    /**
     *
     * @param user_id
     * @param pageNum
     * @param pageSize
     * @return 分页返回某用户的最新博文
     */
    Page<BlogDTO> getBlogPageByUser_idOrderByTime(Integer user_id,Integer pageNum,Integer pageSize);

}
