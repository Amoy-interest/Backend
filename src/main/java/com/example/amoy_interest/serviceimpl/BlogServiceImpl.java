package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.BlogCommentDao;
import com.example.amoy_interest.dao.BlogCountDao;
import com.example.amoy_interest.dao.BlogDao;
import com.example.amoy_interest.dao.BlogImageDao;
import com.example.amoy_interest.dto.BlogDTO;
import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.entity.BlogImage;
import com.example.amoy_interest.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
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
            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
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
            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
            blogChildImages = blogImageDao.findBlogImageByBlog_id(blog.getReply_blog_id());
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
}
