package com.example.demo.dto;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogComment;
import com.example.demo.entity.BlogCount;
import com.example.demo.entity.BlogImage;
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
    private Integer user_id;  //
    private Integer blog_type;
    private Date blog_time;
    private BlogContentDTO blog_content;
    private BlogChildDTO blog_child;
    private BlogCountDTO blog_count;
    private List<BlogComment> blog_comments;

    public BlogDTO(Blog blog, List<BlogComment> blogComments, BlogCount blogCount, List<BlogImage> blogImages , Blog blogChild, List<BlogImage> blogChildImages) {
        this.setUser_id(user_id);
        this.setBlog_type(blog.getBlog_type());
        this.setBlog_time(blog.getBlog_time());

        this.blog_content.setText(blog.getBlog_text());
        List<String> tmp_blog_images = new ArrayList<>();
        for (BlogImage blogImage : blogImages) {
            tmp_blog_images.add(blogImage.getBlog_image());
        }
        this.blog_content.setImages(tmp_blog_images);

        if (blog.getBlog_type() > 0) {
            this.blog_child.setText(blogChild.getBlog_text());
            List<String> tmp_blog_child_images = new ArrayList<>();
            for (BlogImage blogImage : blogChildImages) {
                tmp_blog_child_images.add(blogImage.getBlog_image());
            }
            this.blog_child.setImages(tmp_blog_child_images);
        }

        this.blog_count = new BlogCountDTO(blogCount);
        // 原先的设计是只返回一级评论。当点击某条评论的时候再获取二级评论。
        this.blog_comments = blogComments;
    }
}

