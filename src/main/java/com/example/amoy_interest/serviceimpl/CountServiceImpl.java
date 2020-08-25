package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.dao.BlogReportDao;
import com.example.amoy_interest.dao.UserCountDao;
import com.example.amoy_interest.dao.UserReportDao;
import com.example.amoy_interest.dto.BlogSingleCountDTO;
import com.example.amoy_interest.dto.UserSingleCountDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.CountService;
import com.example.amoy_interest.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:19
 */
@Service
@Slf4j
public class CountServiceImpl implements CountService {
    @Autowired
    private BlogCountDao blogCountDao;
    @Autowired
    private UserCountDao userCountDao;
    @Autowired
    private BlogReportDao blogReportDao;
    @Autowired
    private UserReportDao userReportDao;
    @Autowired
    private RedisService redisService;
    @Override
    @Transactional
    public void transBlogCountDataFromRedis2DB() {
        List<BlogSingleCountDTO> list = redisService.getBlogCommentCountFromRedis();
        List<BlogCount> list1 = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        for(BlogSingleCountDTO dto:list) {
            integerList.add(dto.getBlog_id());
            BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(dto.getBlog_id());
            if(blogCount != null) {
                blogCount.setComment_count(dto.getCount());
                list1.add(blogCount);
            }else {
                log.error("BlogCount id:" +blogCount.getBlog_id() + "数据库中不存在");
            }
        }
        blogCountDao.saveAll(list1);
        redisService.deleteBlogCommentCount(integerList);
        list = redisService.getBlogForwardCountFromRedis();
        integerList = new ArrayList<>();
        List<BlogCount> list2 = new ArrayList<>();
        for(BlogSingleCountDTO dto:list) {
            integerList.add(dto.getBlog_id());
            BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(dto.getBlog_id());
            if(blogCount != null) {
                blogCount.setForward_count(dto.getCount());
                list2.add(blogCount);
            }else {
                log.error("BlogCount id:" +blogCount.getBlog_id() + "数据库中不存在");
            }
        }
        blogCountDao.saveAll(list2);
        redisService.deleteBlogForwardCount(integerList);
    }

    @Override
    @Transactional
    public void transBlogReportDataFromRedis2DB() {
        List<BlogReport> blogReportList = redisService.getBlogReportDataFromRedis();
        blogReportDao.saveAll(blogReportList);
        List<BlogSingleCountDTO>list = redisService.getBlogReportCountFromRedis();
        List<BlogCount> list3 = new ArrayList<>();
        for(BlogSingleCountDTO dto:list) {
            BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(dto.getBlog_id());
            if(blogCount != null) {
                blogCount.setReport_count(blogCount.getReport_count() + dto.getCount());
                list3.add(blogCount);
            }else {
                log.error("BlogCount id:" +blogCount.getBlog_id() + "数据库中不存在");
            }
        }
        blogCountDao.saveAll(list3);
    }

    @Override
    @Transactional
    public void transUserReporterDataFromRedis2DB() {
        List<UserReport> userReportlist = redisService.getUserReportDataFromRedis();
        userReportDao.saveAll(userReportlist);
        List<UserSingleCountDTO> list = redisService.getUserReportCountFromRedis();
        List<UserCount> list4 = new ArrayList<>();
        for(UserSingleCountDTO dto:list) {
            UserCount userCount = userCountDao.getByUserID(dto.getUser_id());
            if(userCount != null) {
                userCount.setReport_count(userCount.getReport_count() + dto.getCount());
                list4.add(userCount);
            }else {
                log.error("UserCount id:" +userCount.getUser_id() + "数据库中不存在");
            }
        }
        userCountDao.saveAll(list4);
    }

    @Override
    @Transactional
    public void transUserCountDataFromRedis2DB() {
        List<UserSingleCountDTO> list = redisService.getUserBlogCountFromRedis();
        List<UserCount> list1 = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        for(UserSingleCountDTO dto:list) {
            integerList.add(dto.getUser_id());
            integerList.add(dto.getUser_id());
            UserCount userCount = userCountDao.getByUserID(dto.getUser_id());
            if(userCount != null) {
                userCount.setBlog_count(dto.getCount());
                list1.add(userCount);
            }else {
                log.error("UserCount id:" +userCount.getUser_id() + "数据库中不存在");
            }
        }
        userCountDao.saveAll(list1);
        redisService.deleteUserBlogCount(integerList);
        list = redisService.getUserFanCountFromRedis();
        List<UserCount> list2 = new ArrayList<>();
        integerList = new ArrayList<>();
        for(UserSingleCountDTO dto:list) {
            integerList.add(dto.getUser_id());
            UserCount userCount = userCountDao.getByUserID(dto.getUser_id());
            if(userCount != null) {
                userCount.setFan_count(dto.getCount());
                list2.add(userCount);
            }else {
                log.error("UserCount id:" +userCount.getUser_id() + "数据库中不存在");
            }
        }
        userCountDao.saveAll(list2);
        redisService.deleteUserFanCount(integerList);
        list = redisService.getUserFollowCountFromRedis();
        List<UserCount> list3 = new ArrayList<>();
        integerList = new ArrayList<>();
        for(UserSingleCountDTO dto:list) {
            integerList.add(dto.getUser_id());
            UserCount userCount = userCountDao.getByUserID(dto.getUser_id());
            if(userCount != null) {
                userCount.setFollow_count(dto.getCount());
                list3.add(userCount);
            }else {
                log.error("UserCount id:" +userCount.getUser_id() + "数据库中不存在");
            }
        }
        userCountDao.saveAll(list3);
        redisService.deleteUserFollowCount(integerList);
    }
}
