package com.example.amoy_interest.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.msgutils.MsgCode;
import com.example.amoy_interest.msgutils.MsgUtil;
import com.example.amoy_interest.repository.ESBlogRepository;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.CountService;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.VoteService;
import com.example.amoy_interest.utils.HotRank;
import com.example.amoy_interest.utils.UserUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.util.concurrent.EsAbortPolicy;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Autowired
    private UserUtil userUtil;
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogCommentDao blogCommentDao;
    @Autowired
    private BlogCountDao blogCountDao;
    @Autowired
    private BlogHeatDao blogHeatDao;
    @Autowired
    private BlogImageDao blogImageDao;
    @Autowired
    private BlogReportDao blogReportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCountDao userCountDao;
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogVoteDao blogVoteDao;
    @Autowired
    private ESBlogRepository esBlogRepository;
    @Autowired
    private RestHighLevelClient client;
    //    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private TopicBlogDao topicBlogDao;
    @Autowired
    private VoteService voteService;
    @Autowired
    private CountService countService;

    @Override
    @Transactional(readOnly = false)
    public BlogDTO addBlog(BlogAddDTO blogAddDTO) {
        Integer user_id = blogAddDTO.getUser_id();
        Blog blog = new Blog();
        blog.setUser_id(user_id);
        blog.setBlog_type(0);  //原创
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogAddDTO.getText());
        blog.set_deleted(false);
        blog.setCheck_status(0);
        List<Topic> list = topicDao.getTopicListByName(blogAddDTO.getTopic_name());

        blog.setReply(new Blog(0));

        blog = blogDao.saveBlog(blog);
        List<TopicBlog> topicBlogList = new ArrayList<>();
        for (Topic topic : list) {
            topicBlogList.add(new TopicBlog(topic.getTopic_id(), blog.getBlog_id(), topic.getTopic_name()));
        }
        topicBlogList = topicBlogDao.saveAll(topicBlogList);
        blog.setTopics(topicBlogList);
        BlogCount blogCount = new BlogCount(blog.getBlog_id(), 0, 0, 0, 0);
        blogCountDao.saveBlogCount(blogCount);
        List<BlogImage> blogImageList = new ArrayList<>();
        if (!blogAddDTO.getImages().isEmpty()) {
            List<String> images = blogAddDTO.getImages();
            for (String image : images) {
                BlogImage blogImage = new BlogImage(blog.getBlog_id(), image);
                blogImageList.add(blogImage);
            }
        }
        blogImageDao.saveAll(blogImageList);
        blog.setBlogImages(blogImageList);
        blog.setUser(userDao.getById(blogAddDTO.getUser_id()));
        BlogDTO blogDTO = new BlogDTO(blog, blogCount, false);

        //计数
        if (redisService.getUserBlogCountFromRedis(user_id) == null) {
            Integer blog_count = userCountDao.getByUserID(user_id).getBlog_count();
            redisService.setUserBlogCount(user_id, blog_count);
        }
        redisService.incrementUserBlogCount(user_id);
        esBlogRepository.save(new ESBlog(blog));
        return blogDTO;
    }

    @Override
    @Transactional(readOnly = false)
    public BlogDTO forwardBlog(BlogForwardDTO blogForwardDTO) {
        Blog blog = new Blog();
        Integer user_id = blogForwardDTO.getUser_id();
        Integer reply_blog_id = blogForwardDTO.getReply_blog_id();
        blog.setUser_id(user_id);
        blog.setBlog_type(1);
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogForwardDTO.getText());
        blog.set_deleted(false);
        blog.setCheck_status(0);
        Blog blogchild = blogDao.findBlogByBlog_id(reply_blog_id);
        blog.setReply(blogchild);
        blog = blogDao.saveBlog(blog);
        List<TopicBlog> list = blogchild.getTopics();
        List<TopicBlog> topicBlogList = new ArrayList<>();
        for (TopicBlog topicBlog : list) {
            TopicBlog topicBlog1 = new TopicBlog(topicBlog.getTopic_id(), blog.getBlog_id(), topicBlog.getTopic_name());
            topicBlogList.add(topicBlog1);
        }
        topicBlogDao.saveAll(topicBlogList);

        BlogCount blogCount = new BlogCount(blog.getBlog_id(), 0, 0, 0, 0);
        blogCountDao.saveBlogCount(blogCount);
        List<BlogImage> blogImageList = null;
        blog.setBlogImages(blogImageList);
        blog.setUser(userDao.getById(blogForwardDTO.getUser_id()));
        blog.setTopics(topicBlogList);
        BlogDTO blogDTO = new BlogDTO(blog, blogCount, false);
        //计数
        if (redisService.getUserBlogCountFromRedis(user_id) == null) {
            Integer blog_count = userCountDao.getByUserID(user_id).getBlog_count();
            redisService.setUserBlogCount(user_id, blog_count);
        }
        redisService.incrementUserBlogCount(user_id);
        if (redisService.getBlogForwardCountFromRedis(reply_blog_id) == null) {
            Integer forward = blogCountDao.findBlogCountByBlog_id(reply_blog_id).getForward_count();
            redisService.setBlogForwardCount(reply_blog_id, forward);
        }
        redisService.incrementBlogForwardCount(reply_blog_id);
        esBlogRepository.save(new ESBlog(blog));
        return blogDTO;
    }

    @Override
    public int insertToES() {
        List<Blog> blogList = blogDao.getAllBlogs();
        List<ESBlog> esBlogList = new ArrayList<>();
        int ret = 0;
        for (Blog blog : blogList) {
            ret++;
            esBlogList.add(new ESBlog(blog));
        }
        esBlogRepository.saveAll(esBlogList);
        return ret;
//        return 0;
    }

    @Override
    @Transactional(readOnly = false)
    public BlogDTO updateBlog(BlogPutDTO blogPutDTO) {
        Integer user_id = userUtil.getUserId();
        Integer blog_id = blogPutDTO.getBlog_id();
        String text = blogPutDTO.getText();
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        //id不一致，取消更新
        if (blog.getUser_id() != user_id) {
            return null;
        }
        blog.setBlog_text(text);
        //判断有无点赞
        Integer result = redisService.findStatusFromRedis(blog_id, user_id);
        boolean flag = false;
        if (result == 1) {
            flag = true;
        } else if (result == 0) {
            flag = false;
        } else {//redis里没有数据,去数据库拿
            BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
            if (blogVote == null || blogVote.getStatus() == 0) {
                flag = false;
            } else {
                flag = true;
            }
        }
        blog = blogDao.saveBlog(blog);
        esBlogRepository.save(new ESBlog(blog));
        return new BlogDTO(blog, getBlogCount(blog_id), flag);
    }

    @Override
    public Blog findBlogByBlog_id(Integer blog_id) {
        return blogDao.findBlogByBlog_id(blog_id);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteByBlog_id(Integer blog_id) {
        Integer user_id = userUtil.getUserId();
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
//        if(blog == null) {
//            return 0;
//        }
        blog.set_deleted(true);//逻辑删除
        blog = blogDao.saveBlog(blog);
        esBlogRepository.save(new ESBlog(blog));
        //计数
        if (redisService.getUserBlogCountFromRedis(user_id) == null) {
            Integer blog_count = userCountDao.getByUserID(user_id).getBlog_count();
            redisService.setUserBlogCount(user_id, blog_count);
        }
        redisService.decrementUserBlogCount(user_id);
        return 1;
    }

    @Override
    @Transactional(readOnly = false)
    public void incrVoteCount(Integer blog_id) {
        Integer user_id = userUtil.getUserId();

        Integer status = redisService.findStatusFromRedis(blog_id, user_id);
        if (status == -1) {//redis内无数据，查看数据库点赞记录
            BlogVote blogVote = voteService.getByBlogIdAndUserId(blog_id, user_id);
            if (blogVote == null || blogVote.getStatus() == 0) {//未点赞或取消点赞状态，则进行点赞
                redisService.saveVote2Redis(blog_id, user_id);
                if (redisService.getVoteCountFromRedis(blog_id) == null) {
                    //点赞数缓存到redis
                    Integer vote_count = blogCountDao.findBlogCountByBlog_id(blog_id).getVote_count();
                    redisService.setVoteCount(blog_id, vote_count);
                }
                redisService.incrementVoteCount(blog_id);
            }
        } else if (status == 1) {
            //已经点赞过，什么都不做
        } else {
            //取消点赞状态，进行点赞
            redisService.saveVote2Redis(blog_id, user_id);
            if (redisService.getVoteCountFromRedis(blog_id) == null) {
                //点赞数缓存到redis
                Integer vote_count = blogCountDao.findBlogCountByBlog_id(blog_id).getVote_count();
                redisService.setVoteCount(blog_id, vote_count);
            }
            redisService.incrementVoteCount(blog_id);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void incrCommentVoteCount(Integer comment_id) {
        blogCommentDao.incrCommentVoteCount(comment_id);
    }

    @Override
    @Transactional(readOnly = false)
    public void decrVoteCount(Integer blog_id) {
        Integer user_id = userUtil.getUserId();
        Integer status = redisService.findStatusFromRedis(blog_id, user_id);
        if (status == -1) {//redis内无数据，查看数据库点赞记录
            BlogVote blogVote = voteService.getByBlogIdAndUserId(blog_id, user_id);
            if (blogVote == null || blogVote.getStatus() == 1) {
                redisService.cancelVoteFromRedis(blog_id, user_id);
                if (redisService.getVoteCountFromRedis(blog_id) == null) {
                    //点赞数缓存到redis
                    Integer vote_count = blogCountDao.findBlogCountByBlog_id(blog_id).getVote_count();
                    redisService.setVoteCount(blog_id, vote_count);
                }
                redisService.decrementVoteCount(blog_id);
            }
        } else if (status == 0) {
            //已经取消点赞，什么都不做
        } else {
            redisService.cancelVoteFromRedis(blog_id, user_id);
            if (redisService.getVoteCountFromRedis(blog_id) == null) {
                //点赞数缓存到redis
                Integer vote_count = blogCountDao.findBlogCountByBlog_id(blog_id).getVote_count();
                redisService.setVoteCount(blog_id, vote_count);
            }
            redisService.decrementVoteCount(blog_id);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void decrCommentVoteCount(Integer comment_id) {
        blogCommentDao.decrCommentVoteCount(comment_id);
    }


    @Override
    @Transactional(readOnly = false)
    public BlogCommentMultiLevelDTO addBlogComment(CommentPostDTO commentPostDTO) {
        Integer blog_id = commentPostDTO.getBlog_id();
        Integer root_comment_id = commentPostDTO.getRoot_comment_id();
        Integer reply_user_id = commentPostDTO.getReply_user_id();
        String text = commentPostDTO.getText();
        Integer user_id = commentPostDTO.getUser_id();
        BlogComment blogComment = new BlogComment();
        blogComment.setBlog_id(blog_id);
        blogComment.setUser_id(user_id);
        if (root_comment_id == 0) {
            blogComment.setComment_level(1); //一级评论
        } else {
            blogComment.setComment_level(2); //二级评论
        }
        blogComment.setReply_user_id(reply_user_id);
        blogComment.setComment_text(text);
        blogComment.setComment_time(new Date());
        blogComment.setVote_count(0);
        blogComment.set_deleted(false);
        blogComment.setRoot_comment_id(root_comment_id);
        User user = userDao.getById(user_id);
        String nickname = user.getNickname();
        String avatar_path = user.getAvatar_path();
        String reply_user_nickname = null;
        if (reply_user_id != 0) {
            reply_user_nickname = userDao.getById(reply_user_id).getNickname();
        }
        BlogCommentMultiLevelDTO dto = new BlogCommentMultiLevelDTO(blogCommentDao.saveBlogComment(blogComment), nickname, reply_user_nickname, avatar_path);
        //原博文评论数+1
        if (redisService.getBlogCommentCountFromRedis(blog_id) == null) {
            //点赞数缓存到redis
            Integer comment_count = blogCountDao.findBlogCountByBlog_id(blog_id).getComment_count();
            redisService.setBlogCommentCount(blog_id, comment_count);
        }
        redisService.incrementBlogCommentCount(blog_id);
        return dto;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteCommentByComment_id(Integer comment_id) {
        Integer user_id = userUtil.getUserId();
        BlogComment blogComment = blogCommentDao.findCommentByComment_id(comment_id);
        if (user_id != userUtil.getUserId() || blogComment == null) {
            return false;
        }
        blogComment.set_deleted(true);//逻辑删除且不对评论数做处理，参考微博
        blogCommentDao.saveBlogComment(blogComment);
        return true;
    }

    @Override
    public BlogDTO getAllBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        Integer user_id = userUtil.getUserId();
        Integer result = redisService.findStatusFromRedis(blog_id, user_id);
        boolean flag = false;
        if (result == 1) {
            flag = true;
        } else if (result == 0) {
            flag = false;
        } else {//redis里没有数据,去数据库拿
            BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
            if (blogVote == null || blogVote.getStatus() == 0) {
                flag = false;
            } else {
                flag = true;
            }
        }
        return new BlogDTO(blog, getBlogCount(blog_id), flag);
//        return null;
//        List<BlogComment> blogComments = blogCommentDao.findLevel1CommentByBlog_id(blog_id);
//        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
//        List<BlogImage> blogImages = blogImageDao.findBlogImageByBlog_id(blog_id);
//        Blog blogChild = new Blog();
//        List<BlogImage> blogChildImages = null;
//        if (blog.getBlog_type() > 0) {
//            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
//        }
//        return new BlogDTO(blog, blogComments, blogCount, blogImages, blogChild, blogChildImages);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean checkReportedBlog(BlogCheckDTO blogCheckDTO) {
        Blog blog = blogDao.findBlogByBlog_id(blogCheckDTO.getBlog_id());
        blog.setCheck_status(blogCheckDTO.getCheck_status());
        blogDao.saveBlog(blog);
        esBlogRepository.save(new ESBlog(blog));
        return true;
    }

//    @Override
//    public List<BlogDTO> getBlogsByUser_id(Integer user_id) {
//        List<Blog> blogs = blogDao.getBlogsByUser_id(user_id);
//        List<BlogDTO> blogDTOS = new ArrayList<>();
//        for (Blog blog : blogs) {
//            Blog blogChild = new Blog();
//            List<BlogImage> blogChildImages = null;
//            if (blog.getBlog_type() > 0) {
////                blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//                blogChildImages = blogChild.getBlogImages();
//            }
//            blogDTOS.add(
//                    new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
//            );
//        }
//        return blogDTOS;
//    }

//    @Override
//    public List<BlogDTO> getRecommendBlogsByUser_id(Integer user_id) {
//        List<Blog> blogs = blogDao.getAllBlogs();
//        List<BlogDTO> blogDTOS = new ArrayList<>();
//        for (Blog blog : blogs) {
//            Blog blogChild = new Blog();
//            List<BlogImage> blogChildImages = null;
//            if (blog.getBlog_type() > 0) {
////                blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//                blogChildImages = blogChild.getBlogImages();
//            }
//            blogDTOS.add(
//                    new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
//            );
//        }
//        return blogDTOS;
//    }
//
//    @Override
//    public List<BlogDTO> getFollowBlogsByUser_id(Integer user_id) {
//        User user = userDao.getById(user_id);
//        List<UserFollow> userFollows= user.getUserFollow();
//        List<BlogDTO> blogDTOS = new ArrayList<>();
//        for (UserFollow userFollow : userFollows) {
//            List<Blog> blogs = blogDao.getBlogsByUser_id(userFollow.getFollow_id());
//            for (Blog blog : blogs) {
//                Blog blogChild = new Blog();
//                List<BlogImage> blogChildImages = null;
//                if (blog.getBlog_type() > 0) {
////                    blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//                    blogChildImages = blogChild.getBlogImages();
//                }
//                blogDTOS.add(
//                        new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
//                );
//            }
//        }
//        return blogDTOS;
//    }


    @Override
    @Transactional(readOnly = false)
    public boolean reportBlog(BlogReportDTO blogReportDTO) {
        Integer user_id = userUtil.getUserId();
        Integer blog_id = blogReportDTO.getBlog_id();
        String report_reason = blogReportDTO.getReport_reason();
        if (redisService.blogIsReported(blogReportDTO.getBlog_id(), user_id)) {
            redisService.saveBlogReport2Redis(blog_id, user_id, report_reason); //覆盖
        } else {
            //redis没有记录，去数据库查
            BlogReport blogReport = blogReportDao.getByBlogIdAndUserId(blog_id, user_id);
            //刷新举报原因和时间，但是重复举报不增加举报数
            redisService.saveBlogReport2Redis(blog_id, user_id, report_reason); //覆盖
            if (blogReport != null) {
                redisService.incrementBlogReportCount(blog_id);//举报数增加
            }
        }
        return true;
    }


    @Override
    public Page<BlogDTO> getSearchListByBlog_text(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        SearchRequest searchRequest = new SearchRequest("blog");
        List<ESBlog> esBlogList = new ArrayList<>();
        long total = 0;
        try {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("blog_text", keyword),ScoreFunctionBuilders.weightFactorFunction(10)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);//设置最低分数
            BoolQueryBuilder boolBuilder = new BoolQueryBuilder()
                    .must(new MatchQueryBuilder("is_deleted", false)).mustNot(new MatchQueryBuilder("check_status", 2));
            boolBuilder.filter(functionScoreQueryBuilder);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.from((pageNum) * pageSize);
            searchSourceBuilder.size(pageSize);
            QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);
            queryBuilder.field("blog_text");
            //构建高亮体
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<span style={{color:'#FF5722'}}>");
            highlightBuilder.postTags("</span>");
            //高亮字段
            highlightBuilder.field("blog_text");
            //搜索体（添加多个搜索参数）
            searchSourceBuilder.highlighter(highlightBuilder);
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.DESC));
            searchRequest.source(searchSourceBuilder);
            //执行查询
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //获取搜索的文档结果
            SearchHits hit = response.getHits();
            total = hit.getTotalHits().value;
            SearchHit[] hits = hit.getHits();
            for (SearchHit searchHit : hits) {
                //将文档中的每一个对象转换json串值
                String sourceAsString = searchHit.getSourceAsString();
                //将json串值转换成对应的实体对象
                ESBlog esBlog = JSON.parseObject(sourceAsString, ESBlog.class);
                //获取对应的高亮域
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                //高亮字段
                HighlightField highlight = highlightFields.get("blog_text");
                if (highlight != null)
                    esBlog.setBlog_text(highlight.fragments()[0].toString());
                esBlogList.add(esBlog);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<BlogDTO> blogDTOList = new ArrayList<>();
//        List<ESBlog> esBlogList = blogPage.getContent();
        Integer user_id = userUtil.getUserId();
        for (ESBlog esBlog : esBlogList) {
            Integer blog_id = esBlog.getId();
            Integer result = redisService.findStatusFromRedis(blog_id, user_id);
            //统计博文数据
            BlogCount blogCount = getBlogCount(blog_id);

            User user = userDao.getById(esBlog.getUser_id());
            List<BlogImage> blogImageList = blogImageDao.findBlogImageByBlog_id(esBlog.getId());
            Blog reply = blogDao.findBlogByBlog_id(esBlog.getReply_blog_id());
            if (result == 1) {
                blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, true));
            } else if (result == 0) {
                blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));
            } else {//redis里没有数据,去数据库拿
                BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
                if (blogVote == null) {
                    blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));
                } else {
                    if (blogVote.getStatus() == 0) {
                        blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));

                    } else {
                        blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, true));
                    }
                }
            }
//            blogDTOList.add(new BlogDTO(blog));
        }
        return new PageImpl<BlogDTO>(blogDTOList, pageable, total);
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        //分页
//        nativeSearchQueryBuilder.withPageable(pageable);
//        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
//        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("blog_text", keyword),
//                ScoreFunctionBuilders.weightFactorFunction(10)));//权重，可以多权重来搜索
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
//                .must(new MatchQueryBuilder("is_deleted", false)).mustNot(new MatchQueryBuilder("check_status", 2));
//        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
//        filterFunctionBuilders.toArray(builders);
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
//                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
//                .setMinScore(2);//设置最低分数
//        nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
//        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));//按相关度排名
//        NativeSearchQuery searchQuery = nativeSearchQueryBuilder
//                .withQuery(boolQueryBuilder)
//                .withHighlightFields(
//                        new HighlightBuilder.Field("blog_text"),new HighlightBuilder.Field("topics_name"))
//                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>")).build();
//        SearchHits<ESBlog> searchHits = elasticsearchRestTemplate.search(searchQuery, ESBlog.class);
//        if (searchHits.getTotalHits() <= 0) {
//            return new PageImpl<>(null, pageable, 0);
//        }
//        List<ESBlog> esBlogList = new ArrayList<>();
////        List<ESBlog> esBlogList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
////        for(ESBlog esBlog:esBlogList) {
////            esBlog.setBlog_text(searchHits.getSearchHit().getContent());
////        }
//        List<SearchHit<ESBlog>> list = searchHits.getSearchHits();
//        for (SearchHit<ESBlog> searchHit : list) {
//            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
//            ESBlog esBlog = searchHit.getContent();
//            System.out.println(searchHit.toString());
//            System.out.println(searchHit.getHighlightFields().toString());
//            String text = highlightFields.get("blog_text") == null ? searchHit.getContent().getBlog_text() : highlightFields.get("blog_text").get(0);
//            esBlog.setBlog_text(text);
//            esBlogList.add(esBlog);
//        }
        //blogDao.findBlogListByBlog_text(keyword,pageable);
//        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
//        List<BlogDTO> blogDTOList = new ArrayList<>();
////        List<ESBlog> esBlogList = blogPage.getContent();
//        Integer user_id = userUtil.getUserId();
//        for (ESBlog esBlog : esBlogList) {
//            Integer blog_id = esBlog.getId();
//            Integer result = redisService.findStatusFromRedis(blog_id, user_id);
//            //统计博文数据
//            BlogCount blogCount = getBlogCount(blog_id);
//
//            User user = userDao.getById(esBlog.getUser_id());
//            List<BlogImage> blogImageList = blogImageDao.findBlogImageByBlog_id(esBlog.getId());
//            Blog reply = blogDao.findBlogByBlog_id(esBlog.getReply_blog_id());
//            if (result == 1) {
//                blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, true));
//            } else if (result == 0) {
//                blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));
//            } else {//redis里没有数据,去数据库拿
//                BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id, user_id);
//                if (blogVote == null) {
//                    blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));
//                } else {
//                    if (blogVote.getStatus() == 0) {
//                        blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, false));
//
//                    } else {
//                        blogDTOList.add(new BlogDTO(esBlog, user, blogImageList, blogCount, reply, true));
//                    }
//                }
//            }
////            blogDTOList.add(new BlogDTO(blog));
//        }
//        return new PageImpl<BlogDTO>(blogDTOList, pageable, total.value);
    }

    @Override
    public Page<BlogDTO> getListByUser_id(Integer user_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blog_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Blog> blogPage = blogDao.findBlogListByUser_id(user_id, pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getListByTopic_id(Integer topic_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blog_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Blog> blogPage = blogDao.findBlogListByTopic_id(topic_id, pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getBlogPageByGroupName(String groupName, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogDao.getBlogPageByGroupName(groupName, pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogCommentLevel1DTO> getLevel1CommentPage(Integer blog_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "comment_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<BlogComment> blogCommentPage = blogCommentDao.findLevel1CommentListByBlog_id(blog_id, pageable);
        List<BlogComment> blogCommentList = blogCommentPage.getContent();
        List<BlogCommentLevel1DTO> blogCommentLevel1DTOList = new ArrayList<>();
        for (BlogComment blogComment : blogCommentList) {
            //暂时设为true
            boolean flag = true;
            if (blogCommentDao.findOneByRoot_comment_id(blogComment.getRoot_comment_id()) == null)
                flag = false;
            Integer user_id = blogComment.getUser_id();
            User user = userDao.getById(user_id);
            String nickname = user.getNickname();
            String avatar_path = user.getAvatar_path();
            blogCommentLevel1DTOList.add(new BlogCommentLevel1DTO(blogComment, nickname, flag, avatar_path));
        }
        return new PageImpl<BlogCommentLevel1DTO>(blogCommentLevel1DTOList, blogCommentPage.getPageable(), blogCommentPage.getTotalElements());
    }

    @Override
    public Page<BlogCommentMultiLevelDTO> getMultiLevelCommentPage(Integer root_comment_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "comment_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<BlogComment> blogCommentPage = blogCommentDao.findMultiLevelCommentListByComment_id(root_comment_id, pageable);
        List<BlogComment> blogCommentList = blogCommentPage.getContent();
        List<BlogCommentMultiLevelDTO> blogCommentMultiLevelDTOS = new ArrayList<>();
        for (BlogComment blogComment : blogCommentList) {
            Integer user_id = blogComment.getUser_id();
            Integer reply_user_id = blogComment.getReply_user_id();
            User user = userDao.getById(user_id);
            String nickname = user.getNickname();
            String avatar_path = user.getAvatar_path();
            String reply_user_nickname = userDao.getById(reply_user_id).getNickname();
            blogCommentMultiLevelDTOS.add(new BlogCommentMultiLevelDTO(blogComment, nickname, reply_user_nickname, avatar_path));
        }
        return new PageImpl<>(blogCommentMultiLevelDTOS, blogCommentPage.getPageable(), blogCommentPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getReportedBlogsPage(Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"blog_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogDao.findReportedBlogsPage(pageable);
        List<BlogDTO> blogDTOList = convertToReportBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getAllBlogPageOrderByTime(Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blog_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Blog> blogPage = blogDao.getAllBlogPage(pageable);
//        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        //没有token，获取不了user_id
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogDTOList.add(new BlogDTO(blog, getBlogCount(blog.getBlog_id()), false));
        }
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getFollowBlogPageByUser_idOrderByTime(Integer user_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blog_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Blog> blogPage = blogDao.getFollowBlogPageByUser_id(user_id, pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getBlogPageByUser_idOrderByTime(Integer user_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blog_time");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Blog> blogPage = blogDao.getBlogPageByUser_id(user_id, pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> searchReportedBlogsPage(String keyword, Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"blog_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogDao.searchReportedBlogsPage(keyword, pageable);
        List<BlogDTO> blogDTOList = convertToReportBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }


    private List<BlogDTO> convertToBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        boolean flag = false;
        Integer user_id = null;
        if (userUtil.getUserId() == null) {
            flag = true;
        } else {
            user_id = userUtil.getUserId();
        }
        for (Blog blog : blogList) {
            Integer blog_id = blog.getBlog_id();
            Integer result = 0;
            if (!flag) {
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

    private List<BlogDTO> convertToReportBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        boolean flag = false;
        Integer user_id = null;
        if (userUtil.getUserId() == null) {
            flag = true;
        } else {
            user_id = userUtil.getUserId();
        }
        for (Blog blog : blogList) {
            Integer blog_id = blog.getBlog_id();
            Integer result = 0;
            if (!flag) {
                result = redisService.findStatusFromRedis(blog_id, user_id);
            }
            //统计数据
            BlogCount blogCount = getReportBlogCount(blog_id);
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
        if (forward == null || comment == null || vote == null) {
            blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
            if (forward == null) {
                forward = blogCount.getForward_count();
                redisService.setBlogForwardCount(blog_id, forward);
            }
            if (comment == null) {
                comment = blogCount.getComment_count();
                redisService.setBlogCommentCount(blog_id, comment);
            }
            if (vote == null) {
                vote = blogCount.getVote_count();
                redisService.setVoteCount(blog_id, vote);
            }
//            if(report == null) {
//                report = blogCount.getReport_count();
//                redisService.setBlogReportCount(blog_id,report);
//            }
        }
        blogCount = new BlogCount(blog_id, forward, comment, vote, 0);
        return blogCount;
    }

    private BlogCount getReportBlogCount(Integer blog_id) {
        BlogCount blogCount = null;
        Integer forward = redisService.getBlogForwardCountFromRedis(blog_id);
        Integer comment = redisService.getBlogCommentCountFromRedis(blog_id);
        Integer vote = redisService.getVoteCountFromRedis(blog_id);
        blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        Integer report = blogCount.getReport_count();
//        Integer report = redisService.getBlogReportCountFromRedis(blog_id);
        if (forward == null || comment == null || vote == null) {
            blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
            if (forward == null) {
                forward = blogCount.getForward_count();
                redisService.setBlogForwardCount(blog_id, forward);
            }
            if (comment == null) {
                comment = blogCount.getComment_count();
                redisService.setBlogCommentCount(blog_id, comment);
            }
            if (vote == null) {
                vote = blogCount.getVote_count();
                redisService.setVoteCount(blog_id, vote);
            }
        }
        blogCount = new BlogCount(blog_id, forward, comment, vote, report);
        return blogCount;
    }

    @Override
    public Page<BlogDTO> getHotBlogPageByTopic_id(Integer topic_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogHeatDao.getHotBlogByTopic_id(topic_id, pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogList);
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
//        List<BlogHeat> blogHeatList = blogHeatPage.getContent();
//        List<Blog> blogList = new ArrayList<>();
//        for(BlogHeat blogHeat: blogHeatList) {
//            blogList.add(blogDao.findBlogByBlog_id(blogHeat.getBlog_id()));
//        }
//        return new PageImpl<>(convertToBlogDTOList(blogList), blogHeatPage.getPageable(), blogHeatPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getHotBlogPageByUser_id(Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogHeatDao.getHotBlogByUser_id(user_id, pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogList);
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    @Cacheable(value = "blog")
    public Page<BlogDTO> getBlogPageOrderByHot(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Blog> blogPage = blogHeatDao.getHotBlog(pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogList);
        return new PageImpl<>(blogDTOList, blogPage.getPageable(), blogPage.getTotalElements());
    }

    @Override
    public void updateAllBlogHeatAfterTime(Date afterTime) {
        List<BlogHeatParam> list = new ArrayList<>();
        if (afterTime == null) {
            list = blogCountDao.getAllBlogHeatParam();
        } else {
            list = blogCountDao.getBlogHeatParamAfterTime(afterTime);
        }
        List<BlogHeat> blogHeatList = new ArrayList<>();
        for (BlogHeatParam blogHeatParam : list) {
            long up = blogHeatParam.getComment_count() * 5 + blogHeatParam.getVote_count() * 2 + blogHeatParam.getForward_count() * 10;
            long score = (long) HotRank.getHotVal(up, 0, blogHeatParam.getCreate_time());
            blogHeatList.add(new BlogHeat(blogHeatParam.getBlog_id(), (int) score));
        }
        blogHeatDao.saveAll(blogHeatList);
    }
}
