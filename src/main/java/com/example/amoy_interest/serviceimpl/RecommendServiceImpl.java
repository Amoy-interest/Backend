package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.RecommendService;
import com.example.amoy_interest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.amoy_interest.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        List<Blog> recBlogs = recommendBlogsDao.getRecommendBlogsUsingUser_id(user_id);
        if (limit_count < recBlogs.size()) {
            List<Integer> list = recBlogs.stream().map(Blog::getBlog_id).collect(Collectors.toList());
            recommendBlogsDao.updateRecommendBlogs(user_id, list);
            return convertToBlogDTOList(recBlogs);
        } else {
            Random random = new Random();
            List<Blog> recBlogs2 = new ArrayList<>();
            for (int i=0; i<limit_count; i++) {
                int target = random.nextInt(recBlogs.size());
                recBlogs2.add(recBlogs.get(target));
                recBlogs.remove(target);
            }
            List<Integer> list = recBlogs2.stream().map(Blog::getBlog_id).collect(Collectors.toList());
            recommendBlogsDao.updateRecommendBlogs(user_id, list);
            return convertToBlogDTOList(recBlogs2);
        }
    }

    @Override
    public List<BlogDTO> getSimBlogUsingBlog_id(Integer blog_id, int limit_count) {
        List<Blog> recBlogs = simBlogDao.getSimBlogUsingBlog_id(blog_id);
        if (limit_count >= recBlogs.size()) return convertToBlogDTOList(recBlogs);
        else return convertToBlogDTOList(recBlogs.subList(0, limit_count));
    }

    @Override
    public List<User> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, int limit_count) {
        List<User> recUsers = simUserDao.getSimUserUsingUser_id(user_id);
        if (limit_count >= recUsers.size()) return recUsers;
        else return recUsers.subList(0, limit_count);
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
