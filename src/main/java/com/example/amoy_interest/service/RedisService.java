package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.BlogVoteCountDTO;
import com.example.amoy_interest.entity.BlogVote;

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

    /**
     * 获取Redis中存储的点赞数量
     * @param blog_id
     * @return
     */
    Integer getCountFromRedis(Integer blog_id);
}
