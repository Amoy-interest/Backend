package com.example.amoy_interest.jUnit5Test;

import com.alibaba.fastjson.JSONObject;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.daoimpl.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.repository.ESBlogRepository;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.serviceimpl.*;
import com.example.amoy_interest.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BlogServiceTest {
    @InjectMocks
    private BlogServiceImpl blogService;

    private static BlogComment testBlogComment = new BlogComment(1, 1, 1, 1, 1, "1", new Date(), 0, false, 1);
    private static Page<Blog> blogPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 5), 0);

    private static Topic testTopic = new Topic(1, "你好", new Date(), 0, 0, 0, null, null, null, new TopicHeat(1, 10));

    private static List<TopicBlog> testTopicBlogList;

    private static User testUser = new User(1, "鲁迅", "123@163.com", 0, "广东", 0, "无", null);

    private static UserCount testUserCount = new UserCount(1, 0, 0, 0, 0);

    private static BlogCount testBlogCount = new BlogCount(1, 0, 0, 0, 0);

    private static Blog testBlog = new Blog(1, 1, 0, new Date(), "nihao", false, 0, 0);

    @Mock
    private BlogDaoImpl blogDao;
    @Mock
    private BlogCountDaoImpl blogCountDao;
    @Mock
    private BlogImageDaoImpl blogImageDao;
    @Mock
    private BlogCommentDaoImpl blogCommentDao;
    @Mock
    private UserDaoImpl userDao;
    @Mock
    private TopicDaoImpl topicDao;
    @Mock
    private UserUtil userUtil;
    @Mock
    private BlogHeatDaoImpl blogHeatDao;
    @Mock
    private BlogReportDaoImpl blogReportDao;
    @Mock
    private UserCountDaoImpl userCountDao;
    @Mock
    private RedisServiceImpl redisService;
    @Mock
    private BlogVoteDaoImpl blogVoteDao;
    @Mock
    private ESBlogRepository esBlogRepository;
    //    @Mock
//    private RestHighLevelClient client;
    @Mock
    private TopicBlogDaoImpl topicBlogDao;
    @Mock
    private VoteServiceImpl voteService;
//    @Mock
//    private Blog

    @BeforeEach
    public void init() {
        when(userDao.getById(any())).thenReturn(testUser);
        testBlog = new Blog(1, 1, 0, new Date(), "nihao", false, 0, 0);
        when(userUtil.getUserId()).thenReturn(1);
        testTopicBlogList = new ArrayList<>();
        testTopicBlogList.add(new TopicBlog(1, 1, "NIHAO"));
        testBlog.setTopics(new ArrayList<>());
        testBlog.setUser(testUser);
        testBlog.setBlogImages(new ArrayList<>());
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);
    }


    @Test
    public void testAddBlog() {
        Blog blog = new Blog(1, 1, 0, new Date(), "nihao", false, 0, 0);
        List<String> images = new ArrayList<>();
        BlogAddDTO blogAddDTO = new BlogAddDTO("你好", images, images, 1);
        List<Topic> topicList = new ArrayList<>();
        topicList.add(testTopic);
        when(topicDao.getTopicListByName(any())).thenReturn(topicList);
        when(blogDao.saveBlog(any())).thenReturn(blog);
        when(topicBlogDao.saveAll(any())).thenReturn(new ArrayList<>());
        when(blogCountDao.saveBlogCount(any())).thenReturn(null);
        when(blogImageDao.saveAll(any())).thenReturn(null);
        when(userDao.getById(any())).thenReturn(testUser);
        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(10);

        blogService.addBlog(blogAddDTO);
//        verify(blogDao, times(1)).saveBlog(any());

        //增加redisService的返回值: null, 对应源代码138行
        images.add("nini");
        when(userCountDao.getByUserID(any())).thenReturn(testUserCount);
        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(null);
//n
        blogService.addBlog(blogAddDTO);
    }

    @Test
    public void testForwardBlog() {
        Blog blog = new Blog(1, 1, 0, new Date(), "nihao", false, 0, 0);

        blog.setTopics(testTopicBlogList);
        BlogForwardDTO blogForwardDTO = new BlogForwardDTO(1, "Nihao1", 2);
        when(blogDao.findBlogByBlog_id(anyInt())).thenReturn(blog);
        when(blogDao.saveBlog(any())).thenReturn(blog);
        when(userDao.getById(any())).thenReturn(testUser);
        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        blogService.forwardBlog(blogForwardDTO);
        //进if
        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(null);
        when(userCountDao.getByUserID(any())).thenReturn(testUserCount);
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.forwardBlog(blogForwardDTO);
    }

    @Test
    public void testInsertToES() {
        Blog blog = new Blog(1, 1, 0, new Date(), "nihao", false, 0, 0);
        blog.setTopics(new ArrayList<>());
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        when(blogDao.getAllBlogs()).thenReturn(blogList);

    }

    @Test
    public void testUpdateBlog() {
        when(blogDao.findBlogByBlog_id(any())).thenReturn(testBlog);
        when(blogDao.saveBlog(any())).thenReturn(testBlog);
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.updateBlog(new BlogPutDTO(1, "你好啊", new ArrayList<>()));

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        blogService.updateBlog(new BlogPutDTO(1, "你好啊", null));

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 1));
        blogService.updateBlog(new BlogPutDTO(1, "你好啊", null));

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        blogService.updateBlog(new BlogPutDTO(1, "你好啊", null));

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 0));
        blogService.updateBlog(new BlogPutDTO(1, "你好啊", null));
    }

    @Test
    public void testFindBlogByBlog_id() {
        when(blogDao.findBlogByBlog_id(any())).thenReturn(null);
        blogService.findBlogByBlog_id(1);
    }

    @Test
    public void testDeleteByBlog_id() {
        when(blogDao.findBlogByBlog_id(any())).thenReturn(testBlog);
        when(blogDao.saveBlog(any())).thenReturn(testBlog);
        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(null);
        when(userCountDao.getByUserID(any())).thenReturn(testUserCount);
        blogService.deleteByBlog_id(1);

        when(redisService.getUserBlogCountFromRedis(any())).thenReturn(1);
        blogService.deleteByBlog_id(1);
    }

    @Test
    public void testIncrVoteCount() {
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.incrVoteCount(1);
        when(voteService.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        blogService.incrVoteCount(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.incrVoteCount(1);

    }

    @Test
    public void testIncrCommentVoteCount() {
        blogService.incrCommentVoteCount(1);
    }

    @Test
    public void testDecrVoteCount() {
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.decrVoteCount(1);
        when(voteService.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        blogService.decrVoteCount(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.decrVoteCount(1);
    }

    @Test
    public void testDecrCommentVoteCount() {
        blogService.decrCommentVoteCount(1);
    }

    @Test
    public void testAddBlogComment() {
        when(userDao.getById(any())).thenReturn(testUser);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        when(blogCommentDao.saveBlogComment(any())).thenReturn(testBlogComment);
        CommentPostDTO commentPostDTO = new CommentPostDTO(1, 1, 1, 1, "111");
        blogService.addBlogComment(commentPostDTO);
        commentPostDTO.setRoot_comment_id(0);
        commentPostDTO.setReply_user_id(0);
        blogService.addBlogComment(commentPostDTO);
    }

    @Test
    public void testDeleteCommentByComment_id() {
        when(blogCommentDao.findCommentByComment_id(any())).thenReturn(testBlogComment);
        blogService.deleteCommentByComment_id(1);
        when(blogCommentDao.findCommentByComment_id(any())).thenReturn(null);
        blogService.deleteCommentByComment_id(1);
    }

    @Test
    public void testGetAllBlogDetail() {
        when(blogDao.findBlogByBlog_id(any())).thenReturn(testBlog);
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.getAllBlogDetail(1);
        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        blogService.getAllBlogDetail(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 1));
        blogService.getAllBlogDetail(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        blogService.getAllBlogDetail(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 0));
        blogService.getAllBlogDetail(1);
    }

    @Test
    public void testCheckReportedBlog() {
        when(blogDao.findBlogByBlog_id(any())).thenReturn(testBlog);
    }

    @Test
    public void testReportBlog() {
        BlogReportDTO blogReportDTO = new BlogReportDTO(1, "notgood");
        when(redisService.blogIsReported(any(), any())).thenReturn(true);
        blogService.reportBlog(blogReportDTO);

        when(redisService.blogIsReported(any(), any())).thenReturn(false);
        when(blogReportDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogReport(1, 1, "d"));
        blogService.reportBlog(blogReportDTO);

        when(redisService.blogIsReported(any(), any())).thenReturn(false);
        when(blogReportDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        blogService.reportBlog(blogReportDTO);
    }

//    @Test
//    public void testGetSearchListByBlog_test() {
//        when(blogImageDao.findBlogImageByBlog_id(any())).thenReturn(new ArrayList<>());
//        when(blogDao.findBlogByBlog_id(any())).thenReturn(testBlog);
//
//        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
//        blogService.getSearchListByBlog_text("寒星", 0, 5);
//
//        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
//        blogService.getSearchListByBlog_text("寒星", 0, 5);
//
//        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 1));
//        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
//        blogService.getSearchListByBlog_text("寒星", 0, 5);
//
//        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
//        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
//        blogService.getSearchListByBlog_text("寒星", 0, 5);
//    }

    @Test
    public void testGetListByUser_id() {
        Page<Blog> blogPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 5), 0);
        when(blogDao.findBlogListByUser_id(any(), any())).thenReturn(blogPage);
        blogService.getListByUser_id(1, 0, 5);
    }

    @Test
    public void testGetListByTopic_id() {
        Page<Blog> blogPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 5), 0);
        when(blogDao.findBlogListByTopic_id(any(), any())).thenReturn(blogPage);
        blogService.getListByTopic_id(1, 0, 5);
    }

    @Test
    public void testGetBlogPageByGroupName() {
        when(blogDao.getBlogPageByGroupName(any(), any())).thenReturn(blogPage);
        blogService.getBlogPageByGroupName("你好", 0, 5);
    }

    @Test
    public void testGetLevel1CommentPage() {
        List<BlogComment> list = new ArrayList<>();
        list.add(testBlogComment);
        Page<BlogComment> blogCommentPage = new PageImpl<>(list, PageRequest.of(0, 5), 1);
        when(blogCommentDao.findLevel1CommentListByBlog_id(any(), any())).thenReturn(blogCommentPage);
        when(blogCommentDao.findOneByRoot_comment_id(any())).thenReturn(null);
        blogService.getLevel1CommentPage(1, 0, 5);
    }

    @Test
    public void testGetMultiLevelCommentPage() {
        when(userDao.getById(any())).thenReturn(testUser);
        List<BlogComment> list = new ArrayList<>();
        list.add(testBlogComment);
        when(blogCommentDao.findOneByRoot_comment_id(any())).thenReturn(null);
        Page<BlogComment> blogCommentPage = new PageImpl<>(list, PageRequest.of(0, 5), 1);
        when(blogCommentDao.findMultiLevelCommentListByComment_id(any(), any())).thenReturn(blogCommentPage);
        blogService.getMultiLevelCommentPage(1, 0, 5);
    }

    @Test
    public void testGetReportedBlogsPAGE() {
        when(blogDao.findReportedBlogsPage(any())).thenReturn(blogPage);
        blogService.getReportedBlogsPage(0, 5, 0);
    }

    @Test
    public void testGetAllBlogPageOrderByTime() {
        List<Blog> list = new ArrayList<>();
        list.add(testBlog);
        Page<Blog> blogPage = new PageImpl<>(list, PageRequest.of(0, 5), 1);
        when(blogDao.getAllBlogPage(any())).thenReturn(blogPage);
        blogService.getAllBlogPageOrderByTime(0, 5);
    }

    @Test
    public void testGetFollowBlogPageByUser_idOrderByTime() {
        when(blogDao.getFollowBlogPageByUser_id(any(), any())).thenReturn(blogPage);
        blogService.getFollowBlogPageByUser_idOrderByTime(1, 0, 5);
    }

    @Test
    public void testGetBlogPageByUser_idOrderByTime() {
        when(blogDao.getBlogPageByUser_id(any(), any())).thenReturn(blogPage);
        blogService.getBlogPageByUser_idOrderByTime(1, 0, 5);
    }

    @Test
    public void testSearchReportedBlogsPage() {
        when(blogDao.searchReportedBlogsPage(any(), any())).thenReturn(blogPage);
        blogService.searchReportedBlogsPage("nihao", 0, 5, 0);
    }

    @Test
    public void testgetHotBlogPageByTopic_id() {
        when(blogHeatDao.getHotBlogByTopic_id(any(), any())).thenReturn(blogPage);
        blogService.getHotBlogPageByTopic_id(1, 0, 5);
    }

    @Test
    public void testGetHotBlogPageByUser_id() {
        when(blogHeatDao.getHotBlogByUser_id(any(), any())).thenReturn(blogPage);
        blogService.getHotBlogPageByUser_id(1, 0, 5);
    }

    @Test
    public void testGetBlogPageOrderByHot() {
        when(blogHeatDao.getHotBlog(any())).thenReturn(blogPage);
        blogService.getBlogPageOrderByHot(0, 5);
    }

    @Test
    public void testupdateAllBlogHeatAfterTime() {
        List<BlogHeatParam> list = new ArrayList<>();
        list.add(new BlogHeatParam(1, 1, 1, 1, new Date()));
        when(blogCountDao.getAllBlogHeatParam()).thenReturn(list);
        when(blogCountDao.getBlogHeatParamAfterTime(any())).thenReturn(list);
        blogService.updateAllBlogHeatAfterTime(null);
        blogService.updateAllBlogHeatAfterTime(new Date());
    }

    @Test
    public void testConvert() {
        List<Blog> blogList = new ArrayList<>();
        blogList.add(testBlog);
        when(userUtil.getUserId()).thenReturn(null);
        blogService.convertToBlogDTOList(blogList);

        when(userUtil.getUserId()).thenReturn(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.convertToBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        blogService.convertToBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 1));
        blogService.convertToBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        blogService.convertToBlogDTOList(blogList);

    }

    @Test
    public void testConvert2() {
        List<Blog> blogList = new ArrayList<>();
        blogList.add(testBlog);
        when(userUtil.getUserId()).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.convertToReportBlogDTOList(blogList);

        when(userUtil.getUserId()).thenReturn(1);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(1);
        blogService.convertToReportBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(0);
        blogService.convertToReportBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(new BlogVote(1, 1, 1));
        blogService.convertToReportBlogDTOList(blogList);

        when(redisService.findStatusFromRedis(any(), any())).thenReturn(-1);
        when(blogVoteDao.getByBlogIdAndUserId(any(), any())).thenReturn(null);
        blogService.convertToReportBlogDTOList(blogList);
    }

    @Test
    public void testGetBlogCount() {
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(null);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getBlogCount(1);

        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(null);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getBlogCount(1);


        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getBlogCount(1);

        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getBlogCount(1);
    }

    @Test
    public void testGetBlogCount2() {
        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(null);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getReportBlogCount(1);

        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(null);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getReportBlogCount(1);


        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(null);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getReportBlogCount(1);

        when(redisService.getBlogForwardCountFromRedis(any())).thenReturn(1);
        when(redisService.getBlogCommentCountFromRedis(any())).thenReturn(1);
        when(redisService.getVoteCountFromRedis(any())).thenReturn(1);
        when(blogCountDao.findBlogCountByBlog_id(any())).thenReturn(testBlogCount);
        blogService.getReportBlogCount(1);

    }
}
