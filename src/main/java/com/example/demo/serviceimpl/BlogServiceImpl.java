package com.example.demo.serviceimpl;

import com.example.demo.dao.BlogDao;
import com.example.demo.dto.BlogDTO;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;
import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

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
    public List<BlogComment> findLevel1CommentByBlog_id(Integer blog_id) {
        return blogDao.findLevel1CommentByBlog_id(blog_id);
    }

    @Override
    public BlogCount findBlogCountByBlog_id(Integer blog_id) {
        return blogDao.findBlogCountByBlog_id(blog_id);
    }

    @Override
    public List<BlogImage> findBlogImageByBlog_id(Integer blog_id) {
        return blogDao.findBlogImageByBlog_id(blog_id);
    }

    @Override
    public  void incrVoteCount(Integer blog_id) {
        blogDao.incrVoteCount(blog_id);
    }

    @Override
    public void incrCommentVoteCount(Integer comment_id) {
        blogDao.incrCommentVoteCount(comment_id);
    }

    @Override
    public BlogComment addBlogComment(BlogComment blogComment) {
        return blogDao.saveBlogComment(blogComment);
    }

    @Override
    public BlogDTO getSimpleBlogDetail(Integer blog_id) {
        Blog blog = blogDao.findBlogByBlog_id(blog_id);
        BlogCount blogCount = blogDao.findBlogCountByBlog_id(blog_id);
        List<BlogImage> blogImages = blogDao.findBlogImageByBlog_id(blog_id);
        Blog blogChild = new Blog();
        List<BlogImage> blogChildImages = null;
        if (blog.getBlog_type() > 0) {
            blogChild = blogDao.findBlogByBlog_id(blog.getReply_blog_id());
            blogChildImages = blogDao.findBlogImageByBlog_id(blog.getReply_blog_id());
        }
        return new BlogDTO(blog, null, blogCount, blogImages, blogChild, blogChildImages);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogDao.getAllBlogs();
    }
}
