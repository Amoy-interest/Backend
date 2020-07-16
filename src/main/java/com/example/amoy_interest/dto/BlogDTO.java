package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogComment;
import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.entity.BlogImage;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogDto", description = "博文信息")
public class BlogDTO {
    private String nickname;
    private Integer blog_type;
    private Date blog_time;
    private BlogContentDTO blog_content;
    private BlogChildDTO blog_child;
    private BlogCountDTO blog_count;
    private List<BlogComment> blog_comments;

    public BlogDTO(Blog blog, List<BlogComment> blogComments, BlogCount blogCount, List<BlogImage> blogImages , Blog blogChild, List<BlogImage> blogChildImages) {
        if (blog.getUser() != null) this.setNickname(blog.getUser().getNickname());
        this.setBlog_type(blog.getBlog_type());
        this.setBlog_time(blog.getBlog_time());

        List<String> tmp_blog_images = null;
        if (blogImages != null) {
            tmp_blog_images = new ArrayList<>();
            for (BlogImage blogImage : blogImages) {
                tmp_blog_images.add(blogImage.getBlog_image());
            }
        }
        this.blog_content = new BlogContentDTO(blog.getBlog_text(), tmp_blog_images);

        if (blog.getBlog_type() > 0) {
            List<String> tmp_blog_child_images = null;
            if (blogChildImages != null ){
                tmp_blog_child_images = new ArrayList<>();
                for (BlogImage blogImage : blogChildImages) {
                    tmp_blog_child_images.add(blogImage.getBlog_image());
                }
            }
            if (blogChild.getUser() != null)
                this.blog_child = new BlogChildDTO(blogChild.getUser_id(),blogChild.getUser().getNickname(),blogChild.getBlog_text(), tmp_blog_child_images);
            else
                this.blog_child = new BlogChildDTO(blogChild.getUser_id(), null, blogChild.getBlog_text(), tmp_blog_child_images);
        }

        this.blog_count = new BlogCountDTO(blogCount);
        // 原先的设计是只返回一级评论。当点击某条评论的时候再获取二级评论。
        this.blog_comments = blogComments;
    }
}

