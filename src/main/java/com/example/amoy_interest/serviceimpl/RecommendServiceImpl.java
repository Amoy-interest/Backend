package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.SimUserDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.amoy_interest.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.*;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private RecommendBlogsDao recommendBlogsDao;
    @Autowired
    private SimBlogDao simBlogDao;
    @Autowired
    private SimUserDao simUserDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogVoteDao blogVoteDao;
    @Autowired
    private BlogCountDao blogCountDao;
    @Autowired
    private BlogService blogService;

    @Override
    @Transactional(readOnly = false)
    public Page<BlogDTO> getRecommendBlogsUsingUser_id(Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Blog> page = recommendBlogsDao.getRecommendBlogsUsingUser_id(user_id, pageable);
        List<Blog> recBlogs = page.getContent();
        if (recBlogs.size() == 0) {
            return blogService.getBlogPageOrderByHot(pageNum, pageSize);
        } else {
            List<Integer> list = recBlogs.stream().map(Blog::getBlog_id).collect(Collectors.toList());
            recommendBlogsDao.updateRecommendBlogs(user_id, list);
            return  new PageImpl<>(convertToBlogDTOList(recBlogs), page.getPageable(), page.getTotalElements());

        }
    }

    @Override
    public Page<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, int limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        Page<Blog> recBlogs = simBlogDao.getSimBlogUsingBlog_id(blog_id, pageable);
        return new PageImpl<>(convertToBlogDTOList(recBlogs.getContent()), recBlogs.getPageable(), recBlogs.getTotalElements());
    }

    @Override
    public Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, int limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        return simUserDao.getSimUserUsingUser_id(my_user_id, user_id, pageable);
    }

    private List<BlogDTO> convertToBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        boolean flag = false;
        Integer user_id = null;
        if(userUtil.getUserId() == null) {
            flag = true;
        }else {
            user_id = userUtil.getUserId();
        }
        for (Blog blog : blogList) {
            Integer blog_id = blog.getBlog_id();
            Integer result = 0;
            if(!flag) {
                result = redisService.findStatusFromRedis(blog_id, user_id);
            }
            //统计数据
            BlogCount blogCount = getBlogCount(blog_id);
            if (result == 1) {
                blogDTOList.add(new BlogDTO(blog, blogCount, true));
            } else if (result == 0) {
                blogDTOList.add(new BlogDTO(blog, blogCount, false));
            } else {//redis里没有数据,去数据库拿
                BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
                if (blogVote == null || blogVote.getStatus() == 0) {
                    blogDTOList.add(new BlogDTO(blog, blogCount, false));
                } else {
                    blogDTOList.add(new BlogDTO(blog, blogCount, true));
                }
            }
        }
        return blogDTOList;
    }

    private BlogCount getBlogCount(Integer blog_id) {
        BlogCount blogCount = null;
        Integer forward = redisService.getBlogForwardCountFromRedis(blog_id);
        Integer comment = redisService.getBlogCommentCountFromRedis(blog_id);
        Integer vote = redisService.getVoteCountFromRedis(blog_id);
//        Integer report = redisService.getBlogReportCountFromRedis(blog_id);
        if(forward == null || comment == null || vote == null ) {
            blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
            if(forward == null) {
                forward = blogCount.getForward_count();
                redisService.setBlogForwardCount(blog_id,forward);
            }
            if(comment == null) {
                comment = blogCount.getComment_count();
                redisService.setBlogCommentCount(blog_id,comment);
            }
            if(vote == null) {
                vote = blogCount.getVote_count();
                redisService.setVoteCount(blog_id,vote);
            }
//            if(report == null) {
//                report = blogCount.getReport_count();
//                redisService.setBlogReportCount(blog_id,report);
//            }
        }
        blogCount = new BlogCount(blog_id,forward,comment,vote,0);
        return blogCount;
    }
}
