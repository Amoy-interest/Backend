package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.dao.BlogVoteDao;
import com.example.amoy_interest.dto.BlogVoteCountDTO;
import com.example.amoy_interest.dto.VoteDTO;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.entity.BlogVote;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/7/29 15:23
 */
@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Autowired
    private BlogVoteDao blogVoteDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogCountDao blogCountDao;

    @Override
    @Transactional(readOnly = false)
    public BlogVote save(BlogVote blogVote) {
        return blogVoteDao.save(blogVote);
    }

    @Override
    @Transactional(readOnly = false)
    public List<BlogVote> saveAll(List<BlogVote> list) {
        return blogVoteDao.saveAll(list);
    }

    @Override
    public Page<BlogVote> getVoteListByBlogId(Integer blog_id, Pageable pageable) {
        return null;
    }

    @Override
    public Page<BlogVote> getVoteListByUserId(Integer user_id, Pageable pageable) {
        return null;
    }

    @Override
    public BlogVote getByBlogIdAndUserId(Integer blog_id, Integer user_id) {
        return blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
    }

    @Override
    @Transactional(readOnly = false)
    public void transVoteFromRedis2DB() {
        List<BlogVote> list = redisService.getVoteDataFromRedis();
//        for (BlogVote blogVote : list) {
//            BlogVote bv = getByBlogIdAndUserId(blogVote.getBlog_id(), blogVote.getUser_id());
//            if (bv == null) {
//                save(blogVote);
//            } else {
//                bv.setStatus(blogVote.getStatus());
//                save(bv);
//            }
//        }
        saveAll(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void transVoteCountFromRedis2DB() {
        List<BlogVoteCountDTO> list = redisService.getVoteCountFromRedis();
        List<BlogCount> blogCountList = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (BlogVoteCountDTO dto : list) {
            list2.add(dto.getBlog_id());
            BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(dto.getBlog_id());
            if(blogCount != null) {
                blogCount.setVote_count(dto.getVote_count());
                blogCountList.add(blogCount);
            }else {
                log.error("无BlogCount记录");
            }
        }
        if(!blogCountList.isEmpty()) {
            blogCountDao.saveAll(blogCountList);
        }
        //存到mysql后再删除数据，防止redis取了mysql旧数据
        redisService.deleteVoteCount(list2);
    }
}
