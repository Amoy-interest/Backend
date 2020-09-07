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
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public Page<BlogDTO> getRecommendBlogsUsingUser_id(Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Blog> page = recommendBlogsDao.getRecommendBlogsUsingUser_id(user_id, pageable);
        List<Blog> recBlogs = page.getContent();
        if (recBlogs.size() == 0) {
            return blogService.getBlogPageOrderByHot(pageNum, pageSize);
        } else {
            List<Integer> list = recBlogs.stream().map(Blog::getBlog_id).collect(Collectors.toList());
            recommendBlogsDao.updateRecommendBlogs(user_id, list);
            return  new PageImpl<>(blogService.convertToBlogDTOList(recBlogs), page.getPageable(), page.getTotalElements());

        }
    }

    @Override
    public Page<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, Integer limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        Page<Blog> recBlogs = simBlogDao.getSimBlogUsingBlog_id(blog_id, pageable);
        return new PageImpl<>(blogService.convertToBlogDTOList(recBlogs.getContent()), recBlogs.getPageable(), recBlogs.getTotalElements());
    }

    @Override
    public Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, Integer limit_count) {
        Pageable pageable = PageRequest.of(0, limit_count);
        return simUserDao.getSimUserUsingUser_id(my_user_id, user_id, pageable);
    }

}
