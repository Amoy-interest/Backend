package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.BlogCommentLevel1DTO;
import com.example.amoy_interest.dto.BlogCommentMultiLevelDTO;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

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

    @Override
    public Blog addBlog(Blog blog) {

        return blogDao.saveBlog(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        return blogDao.saveBlog(blog);
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
    public  void incrVoteCount(Integer blog_id) {
        blogCountDao.incrVoteCount(blog_id);
    }

    @Override
    public void incrCommentVoteCount(Integer comment_id) {
        blogCommentDao.incrCommentVoteCount(comment_id);
    }

    @Override
    public  void decrVoteCount(Integer blog_id) {
        blogCountDao.decrVoteCount(blog_id);
    }

    @Override
    public void decrCommentVoteCount(Integer comment_id) {
        blogCommentDao.decrCommentVoteCount(comment_id);
    }

    @Override
    public BlogComment addBlogComment(BlogComment blogComment) {
        return blogCommentDao.saveBlogComment(blogComment);
    }

    @Override
    public void deleteCommentByComment_id(Integer comment_id) {
        blogCommentDao.deleteByComment_id(comment_id);
    }

    @Override
    public BlogDTO getSimpleBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        List<BlogImage> blogImages = blogImageDao.findBlogImageByBlog_id(blog_id);
        Blog blogChild = new Blog();
        List<BlogImage> blogChildImages = null;
        if (blog.getBlog_type() > 0) {
//            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
        }
        return new BlogDTO(blog, null, blogCount, blogImages, blogChild, blogChildImages);
    }

    @Override
    public BlogDTO getAllBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        List<BlogComment> blogComments = blogCommentDao.findLevel1CommentByBlog_id(blog_id);
        BlogCount blogCount = blogCountDao.findBlogCountByBlog_id(blog_id);
        List<BlogImage> blogImages = blogImageDao.findBlogImageByBlog_id(blog_id);
        Blog blogChild = new Blog();
        List<BlogImage> blogChildImages = null;
        if (blog.getBlog_type() > 0) {
//            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
//            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
        }
        return new BlogDTO(blog, blogComments, blogCount, blogImages, blogChild, blogChildImages);
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
            String nickname = userDao.getById(user_id).getNickname();
            blogCommentLevel1DTOList.add(new BlogCommentLevel1DTO(blogComment,nickname,flag));
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
            String nickname = userDao.getById(user_id).getNickname();
            String reply_user_nickname = userDao.getById(reply_user_id).getNickname();
            blogCommentMultiLevelDTOS.add(new BlogCommentMultiLevelDTO(blogComment,nickname,reply_user_nickname));
        }
        return new PageImpl<>(blogCommentMultiLevelDTOS,blogCommentPage.getPageable(),blogCommentPage.getTotalElements());
    }

    @Override
    public Page<BlogDTO> getReportedBlogsPage(Integer pageNum, Integer pageSize) {
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
        List<BlogDTO> blogDTOList = convertToBlogDTOList(blogPage.getContent());
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


    private List<BlogDTO> convertToBlogDTOList(List<Blog> blogList) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        for(Blog blog:blogList) {
            blogDTOList.add(new BlogDTO(blog));
        }
        return blogDTOList;
    }
}
