package com.example.amoy_interest.service;

import com.example.amoy_interest.entity.BlogVote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/29 15:10
 */
public interface VoteService {

    /**
     * 保存点赞记录
     * @param blogVote
     * @return
     */
    BlogVote save(BlogVote blogVote);

    /**
     * 批量保存或修改
     * @param list
     */
    List<BlogVote> saveAll(List<BlogVote> list);


    /**
     * 根据博文的id查询点赞列表（即查询都谁给这个博文点赞过）
     * @param blog_id
     * @param pageable
     * @return
     */
    Page<BlogVote> getVoteListByBlogId(Integer blog_id, Pageable pageable);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给哪些博文点赞过）
     * @param user_id
     * @param pageable
     * @return
     */
    Page<BlogVote> getVoteListByUserId(Integer user_id, Pageable pageable);

    /**
     * 通过被博文和点赞人id查询是否存在点赞记录
     * @param blog_id
     * @param user_id
     * @return
     */
    BlogVote getByBlogIdAndUserId(Integer blog_id,Integer user_id);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transVoteFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transVoteCountFromRedis2DB();
}
