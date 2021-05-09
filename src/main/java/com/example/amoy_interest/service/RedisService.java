package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogSingleCountDTO;
import com.example.amoy_interest.dto.BlogVoteCountDTO;
import com.example.amoy_interest.dto.UserSingleCountDTO;
import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.entity.UserReport;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/29 11:34
 * https://blog.csdn.net/CSDN_bang/article/details/103257600
 */
public interface RedisService {

    /**
     * 点赞。状态为1
     * @param blog_id
     * @param user_id
     */
    void saveVote2Redis(Integer blog_id,Integer user_id);

    /**
     * 举报，key为blog_id::user_id
     * @param blog_id
     * @param user_id
     * @param report_reason
     */
    void saveBlogReport2Redis(Integer blog_id,Integer user_id,String report_reason);

    void saveUserReport2Redis(Integer user_id,Integer reporter_id,String report_reason);

    void incrementUserFollowCount(Integer user_id);

    void incrementUserFanCount(Integer user_id);

    void incrementUserBlogCount(Integer user_id);

    void incrementBlogCommentCount(Integer blog_id);

    void incrementBlogReportCount(Integer blog_id);

    void incrementUserReportCount(Integer user_id);

    void decrementUserFollowCount(Integer user_id);

    void decrementUserFanCount(Integer user_id);

    void decrementUserBlogCount(Integer user_id);

    void decrementBlogCommentCount(Integer blog_id);

    void decrementBlogReportCount(Integer blog_id);

    void decrementUserReportCount(Integer user_id);

    void incrementBlogForwardCount(Integer blog_id);

    void decrementBlogForwardCount(Integer blog_id);

    /**
     * 取消点赞。将状态改变为0
     * @param blog_id
     * @param user_id
     */
    void cancelVoteFromRedis(Integer blog_id,Integer user_id);

    /**
     * 从Redis中删除一条点赞数据
     * @param blog_id
     * @param user_id
     */
    void deleteVoteFromRedis(Integer blog_id,Integer user_id);

    /**
     * 从Redis中查找一条点赞数据
     * @param blog_id
     * @param user_id
     * @return
     */
    Integer findStatusFromRedis(Integer blog_id,Integer user_id);

    /**
     * 从Redis中查找博文是否已经被某用户举报过
     * @param blog_id
     * @param user_id
     * @return
     */
    boolean blogIsReported(Integer blog_id,Integer user_id);

    /**
     * 从Redis中查找用户是否已经被某用户举报过
     * @param user_id
     * @param reporter_id
     * @return
     */
    boolean userIsReported(Integer user_id,Integer reporter_id);

    /**
     * 该博文点赞数+1
     * @param blog_id
     */
    void incrementVoteCount(Integer blog_id);

    /**
     * 该博文点赞数-1
     * @param blog_id
     */
    void decrementVoteCount(Integer blog_id);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<BlogVote> getVoteDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<BlogVoteCountDTO> getVoteCountFromRedis();

    List<BlogReport> getBlogReportDataFromRedis();

    List<UserReport> getUserReportDataFromRedis();

    List<BlogSingleCountDTO> getBlogReportCountFromRedis();

    List<BlogSingleCountDTO> getBlogForwardCountFromRedis();

    List<BlogSingleCountDTO> getBlogCommentCountFromRedis();

    List<UserSingleCountDTO> getUserFollowCountFromRedis();

    List<UserSingleCountDTO> getUserFanCountFromRedis();

    List<UserSingleCountDTO> getUserBlogCountFromRedis();

    List<UserSingleCountDTO> getUserReportCountFromRedis();

    /**
     * 获取Redis中存储的点赞数量
     * @param blog_id
     * @return
     */
    Integer getVoteCountFromRedis(Integer blog_id);

    Integer getBlogCommentCountFromRedis(Integer blog_id);

    Integer getBlogForwardCountFromRedis(Integer blog_id);

    Integer getUserFollowCountFromRedis(Integer user_id);

    Integer getUserFanCountFromRedis(Integer user_id);

    Integer getUserBlogCountFromRedis(Integer user_id);

    void setVoteCount(Integer blog_id,Integer count);

    void setBlogCommentCount(Integer blog_id,Integer count);

    void setBlogForwardCount(Integer blog_id,Integer count);

    void setUserFollowCount(Integer user_id,Integer count);

    void setUserFanCount(Integer user_id,Integer count);

    void setUserBlogCount(Integer user_id,Integer count);

    void deleteVoteCount(List<Integer> list);

    void deleteBlogCommentCount(List<Integer> list);

    void deleteBlogForwardCount(List<Integer> list);

    void deleteUserFollowCount(List<Integer> list);

    void deleteUserFanCount(List<Integer> list);

    void deleteUserBlogCount(List<Integer> list);

}
