package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.dto.SimUserDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    @Transactional
    public List<BlogDTO> getRecommendBlogsUsingUser_id(Integer user_id, int limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        List<Blog> recBlogs = (recommendBlogsDao.getRecommendBlogsUsingUser_id(user_id, pageable)).getContent();
        List<Integer> list = recBlogs.stream().map(Blog::getBlog_id).collect(Collectors.toList());
        recommendBlogsDao.updateRecommendBlogs(user_id, list);
        return convertToBlogDTOList(recBlogs);
    }

    @Override
    public List<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, int limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        Page<Blog> recBlogs = simBlogDao.getSimBlogUsingBlog_id(blog_id, pageable);
        return convertToBlogDTOList(recBlogs.getContent());
    }

    @Override
    public List<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, int limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        return (simUserDao.getSimUserUsingUser_id(my_user_id, user_id, pageable)).getContent();
    }

    private List<BlogDTO> convertToBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        Integer user_id = userUtil.getUserId();
        for (Blog blog : blogList) {
            Integer blog_id = blog.getBlog_id();
            Integer result = redisService.findStatusFromRedis(blog_id, user_id);

            //统计数据
            BlogCount blogCount = CalculateCount(blog_id);
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

    private BlogCount CalculateCount(Integer blog_id) {
        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        blogCount.setForward_count(blogCount.getForward_count() + redisService.getBlogForwardCountFromRedis(blog_id));
        blogCount.setComment_count(blogCount.getComment_count() + redisService.getBlogCommentCountFromRedis(blog_id));
        blogCount.setVote_count(blogCount.getVote_count() + redisService.getVoteCountFromRedis(blog_id));
        return blogCount;
    }
}
