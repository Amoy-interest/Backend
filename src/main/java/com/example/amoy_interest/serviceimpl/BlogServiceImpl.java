package com.example.amoy_interest.serviceimpl;

import com.auth0.jwt.JWT;
import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.BlogService;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.utils.UserUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
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
    private BlogImageDao blogImageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogVoteDao blogVoteDao;


    @Override
    @Transactional
    public BlogDTO addBlog(BlogAddDTO blogAddDTO) {
        Blog blog = new Blog();
        blog.setUser_id(blogAddDTO.getUser_id());
        blog.setBlog_type(0);  //原创
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogAddDTO.getText());
        blog.set_deleted(false);
        blog.setCheck_status(0);
        blog.setTopic_id(blogAddDTO.getTopic_id());

        blog.setReply(new Blog(0));
        blog = blogDao.saveBlog(blog);
        BlogCount blogCount = new BlogCount(blog.getBlog_id(),0,0,0,0);
        blogCountDao.saveBlogCount(blogCount);
        List<BlogImage> blogImageList = new ArrayList<>();
        if(!blogAddDTO.getImages().isEmpty()) {
            List<String> images = blogAddDTO.getImages();
            for(String image:images) {
                BlogImage blogImage = new BlogImage(blog.getBlog_id(),image);//暂时只存一张
                blogImage = blogImageDao.save(blogImage);
                blogImageList.add(blogImage);
            }
        }
        blog.setBlogCount(blogCount);
        blog.setBlogImages(blogImageList);
        blog.setUser(userDao.getById(blogAddDTO.getUser_id()));
        return new BlogDTO(blog,false);
    }
    @Override
    @Transactional
    public BlogDTO forwardBlog(BlogForwardDTO blogForwardDTO) {
        Blog blog = new Blog();
//        blog.setReplyBlogId(blogForwardDTO.getReply_blog_id());
        blog.setUser_id(blogForwardDTO.getUser_id());
        blog.setBlog_type(1);
        blog.setBlog_time(new Date());
        blog.setBlog_text(blogForwardDTO.getText());
        blog.set_deleted(false);
        blog.setCheck_status(0);
        Blog blogchild = blogDao.findBlogByBlog_id(blogForwardDTO.getReply_blog_id());
        blog.setTopic_id(blogchild.getTopic_id());
        blog.setReply(blogchild);
        blog = blogDao.saveBlog(blog);
        //原博文转发数+1
        BlogCount childCount = blogchild.getBlogCount();
        childCount.setForward_count(childCount.getForward_count() + 1);
        blogCountDao.saveBlogCount(childCount);

        BlogCount blogCount = new BlogCount(blog.getBlog_id(),0,0,0,0);
        blogCountDao.saveBlogCount(blogCount);
        List<BlogImage> blogImageList = null;
        blog.setBlogCount(blogCount);
        blog.setBlogImages(blogImageList);
        blog.setUser(userDao.getById(blogForwardDTO.getUser_id()));
        return new BlogDTO(blog,false);
    }


    @Override
    public BlogDTO updateBlog(BlogPutDTO blogPutDTO) {
        Integer blog_id = blogPutDTO.getBlog_id();
        String text = blogPutDTO.getText();
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        blog.setBlog_text(text);

        //判断有无点赞
//        redisService.findStatusFromRedis(blog_id,)
        return new BlogDTO(blogDao.saveBlog(blog));
    }

    @Override
    public Blog findBlogByBlog_id(Integer blog_id) {
        return blogDao.findBlogByBlog_id(blog_id);
    }

    @Override
    public void deleteByBlog_id(Integer blog_id) {
        blogDao.deleteByBlog_id(blog_id);
    }

    @Override
    @Transactional
    public  void incrVoteCount(Integer blog_id) {
        blogCountDao.incrVoteCount(blog_id);
    }

    @Override
    @Transactional
    public void incrCommentVoteCount(Integer comment_id) {
        blogCommentDao.incrCommentVoteCount(comment_id);
    }

    @Override
    @Transactional
    public  void decrVoteCount(Integer blog_id) {
        blogCountDao.decrVoteCount(blog_id);
    }

    @Override
    @Transactional
    public void decrCommentVoteCount(Integer comment_id) {
        blogCommentDao.decrCommentVoteCount(comment_id);
    }


    @Override
    @Transactional
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
        if(reply_user_id != 0) {
            reply_user_nickname = userDao.getById(reply_user_id).getNickname();
        }
        //原博文评论数+1
        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        blogCount.setComment_count(blogCount.getComment_count() + 1);
        blogCountDao.saveBlogCount(blogCount);
        return new BlogCommentMultiLevelDTO(blogCommentDao.saveBlogComment(blogComment),nickname,reply_user_nickname,avatar_path);
    }

    @Override
    public void deleteCommentByComment_id(Integer comment_id) {
        blogCommentDao.deleteByComment_id(comment_id);
    }

    @Override
    public BlogDTO getSimpleBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
//        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
//        List<BlogImage> blogImages = blogImageDao.findBlogImageByBlog_id(blog_id);
//        Blog blogChild = new Blog();
//        List<BlogImage> blogChildImages = null;
//        if (blog.getBlog_type() > 0) {
////            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
////            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
//        }
        return new BlogDTO(blog);
//        return new BlogDTO(blog, null, blogCount, blogImages, blogChild, blogChildImages);
    }

    @Override
    public BlogDTO getAllBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        return new BlogDTO(blog);
//        List<BlogComment> blogComments = blogCommentDao.findLevel1CommentByBlog_id(blog_id);
//        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
//        List<BlogImage> blogImages = blogImageDao.findBlogImageByBlog_id(blog_id);
//        Blog blogChild = new Blog();
//        List<BlogImage> blogChildImages = null;
//        if (blog.getBlog_type() > 0) {
////            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
////            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
//        }
//        return new BlogDTO(blog, blogComments, blogCount, blogImages, blogChild, blogChildImages);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogDao.getAllBlogs();
    }

    @Override
    public List<BlogCount> getAllReportedBlogs() {
        return blogCountDao.findReportedBlogs();
    }

    @Override
    public boolean checkReportedBlog(BlogCheckDTO blogCheckDTO) {
        Blog blog = blogDao.findBlogByBlog_id(blogCheckDTO.getBlog_id());
        blog.setCheck_status(blogCheckDTO.getCheck_status());
        blogDao.saveBlog(blog);
        return true;
    }

    @Override
    public List<BlogDTO> getBlogsByUser_id(Integer user_id) {
        List<Blog> blogs = blogDao.getBlogsByUser_id(user_id);
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : blogs) {
            Blog blogChild = new Blog();
            List<BlogImage> blogChildImages = null;
            if (blog.getBlog_type() > 0) {
//                blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
                blogChildImages = blogChild.getBlogImages();
            }
            blogDTOS.add(
                    new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
            );
        }
        return blogDTOS;
    }

    @Override
    public List<BlogDTO> getRecommendBlogsByUser_id(Integer user_id) {
        List<Blog> blogs = blogDao.getAllBlogs();
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : blogs) {
            Blog blogChild = new Blog();
            List<BlogImage> blogChildImages = null;
            if (blog.getBlog_type() > 0) {
//                blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
                blogChildImages = blogChild.getBlogImages();
            }
            blogDTOS.add(
                    new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
            );
        }
        return blogDTOS;
    }

    @Override
    public List<BlogDTO> getFollowBlogsByUser_id(Integer user_id) {
        User user = userDao.getById(user_id);
        List<UserFollow> userFollows= user.getUserFollow();
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (UserFollow userFollow : userFollows) {
            List<Blog> blogs = blogDao.getBlogsByUser_id(userFollow.getFollow_id());
            for (Blog blog : blogs) {
                Blog blogChild = new Blog();
                List<BlogImage> blogChildImages = null;
                if (blog.getBlog_type() > 0) {
//                    blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
                    blogChildImages = blogChild.getBlogImages();
                }
                blogDTOS.add(
                        new BlogDTO(blog, null, blog.getBlogCount(), blog.getBlogImages(), blogChild, blogChildImages)
                );
            }
        }
        return blogDTOS;
    }

    @Override
    public boolean reportBlogByBlog_id(Integer blog_id) {
        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        blogCount.setReport_count(blogCount.getReport_count()+1);
        blogCountDao.saveBlogCount(blogCount);
        return true;
    }

    @Override
    public Page<BlogDTO> getSearchListByBlog_text(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Blog> blogPage = blogDao.findBlogListByBlog_text(keyword,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getListByUser_id(Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Blog> blogPage = blogDao.findBlogListByUser_id(user_id,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getListByTopic_id(Integer topic_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Blog> blogPage = blogDao.findBlogListByTopic_id(topic_id,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<BlogDTO>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogCommentLevel1DTO> getLevel1CommentPage(Integer blog_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<BlogComment> blogCommentPage = blogCommentDao.findLevel1CommentListByBlog_id(blog_id,pageable);
        List<BlogComment> blogCommentList = blogCommentPage.getContent();
        List<BlogCommentLevel1DTO> blogCommentLevel1DTOList = new ArrayList<>();
        for(BlogComment blogComment: blogCommentList) {
            //暂时设为true
            boolean flag = true;
            if(blogCommentDao.findOneByRoot_comment_id(blogComment.getRoot_comment_id()) == null)
                flag = false;
            Integer user_id = blogComment.getUser_id();
            User user = userDao.getById(user_id);
            String nickname = user.getNickname();
            String avatar_path = user.getAvatar_path();
            blogCommentLevel1DTOList.add(new BlogCommentLevel1DTO(blogComment,nickname,flag,avatar_path));
        }
        return new PageImpl<BlogCommentLevel1DTO>(blogCommentLevel1DTOList,blogCommentPage.getPageable(),blogCommentPage.getTotalElements());
    }

    @Override
    public Page<BlogCommentMultiLevelDTO> getMultiLevelCommentPage(Integer root_comment_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<BlogComment> blogCommentPage = blogCommentDao.findMultiLevelCommentListByComment_id(root_comment_id,pageable);
        List<BlogComment> blogCommentList = blogCommentPage.getContent();
        List<BlogCommentMultiLevelDTO> blogCommentMultiLevelDTOS = new ArrayList<>();
        for(BlogComment blogComment: blogCommentList) {
            Integer user_id = blogComment.getUser_id();
            Integer reply_user_id = blogComment.getReply_user_id();
            User user = userDao.getById(user_id);
            String nickname = user.getNickname();
            String avatar_path = user.getAvatar_path();
            String reply_user_nickname = userDao.getById(reply_user_id).getNickname();
            blogCommentMultiLevelDTOS.add(new BlogCommentMultiLevelDTO(blogComment,nickname,reply_user_nickname,avatar_path));
        }
        return new PageImpl<>(blogCommentMultiLevelDTOS,blogCommentPage.getPageable(),blogCommentPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getReportedBlogsPage(Integer pageNum, Integer pageSize,Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"blog_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Blog> blogPage = blogDao.findReportedBlogsPage(pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getAllBlogPageOrderByTime(Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog_time");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Blog> blogPage = blogDao.getAllBlogPage(pageable);
//        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        //没有token，获取不了user_id
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList = new ArrayList<>();
        for(Blog blog:blogList) {
            blogDTOList.add(new BlogDTO(blog,false));
        }
        return new PageImpl<>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getFollowBlogPageByUser_idOrderByTime(Integer user_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog_time");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Blog> blogPage = blogDao.getFollowBlogPageByUser_id(user_id,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getBlogPageByUser_idOrderByTime(Integer user_id, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog_time");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Blog> blogPage = blogDao.getBlogPageByUser_id(user_id,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> searchReportedBlogsPage(String keyword, Integer pageNum, Integer pageSize,Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"blog_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Blog> blogPage = blogDao.searchReportedBlogsPage(keyword,pageable);
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
        return new PageImpl<>(blogDTOList,blogPage.getPageable(),blogPage.getTotalElements());
    }




    private List<BlogDTO> convertToBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        Integer user_id = userUtil.getUserId();
        for(Blog blog:blogList) {
            Integer blog_id = blog.getBlog_id();
            Integer result = redisService.findStatusFromRedis(blog_id,user_id);

            //统计点赞数
            BlogCount blogCount = blog.getBlogCount();
            blogCount.setVote_count(blogCount.getVote_count() + redisService.getCountFromRedis(blog_id));
            blog.setBlogCount(blogCount);

            if(result == 1) {
                blogDTOList.add(new BlogDTO(blog,true));
            }else if(result == 0) {
                blogDTOList.add(new BlogDTO(blog,false));
            }else {//redis里没有数据,去数据库拿
                BlogVote blogVote = blogVoteDao.getByBlogIdAndUserId(blog_id,user_id);
                if(blogVote == null) {
                    blogDTOList.add(new BlogDTO(blog,false));
                }else {
                    if(blogVote.getStatus() == 0) {
                        blogDTOList.add(new BlogDTO(blog,false));
                    }else {
                        blogDTOList.add(new BlogDTO(blog,true));
                    }
                }
            }
//            blogDTOList.add(new BlogDTO(blog));
        }
        return blogDTOList;
    }
}
