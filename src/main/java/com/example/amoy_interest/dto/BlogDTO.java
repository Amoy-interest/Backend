package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
    @ApiModelProperty(value = "发此博文的用户的昵称",example = "mok")
    private String nickname;
    @ApiModelProperty(value = "此博文的id")
    private Integer blog_id;
    @ApiModelProperty(value = "此博文的类型(0为原创，1为转发)")
    private Integer blog_type;
    @ApiModelProperty(value = "博文发布时间")
    private Date blog_time;
    @ApiModelProperty(value = "博文的内容")
    private BlogContentDTO blog_content;
    @ApiModelProperty(value = "博文为转发时的转发对象")
    private BlogChildDTO blog_child;
    @ApiModelProperty(value = "博文计数信息")
    private BlogCountDTO blog_count;
    @ApiModelProperty(value = "用户头像")
    private String avatar_path;
    //该方法需要删除
    public BlogDTO(Blog blog, List<BlogComment> blogComments, BlogCount blogCount, List<BlogImage> blogImages , Blog blogChild, List<BlogImage> blogChildImages) {
        if (blog.getUser() != null) this.setNickname(blog.getUser().getNickname());
        this.setBlog_type(blog.getBlog_type());
        this.setBlog_time(blog.getBlog_time());
        this.setBlog_id(blog.getBlog_id());
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
//            if (blogChild.getUser() != null)
//                this.blog_child = new BlogChildDTO(blogChild.getUser_id(),blogChild.getUser().getNickname(),blogChild.getBlog_text(), tmp_blog_child_images);
//            else
//                this.blog_child = new BlogChildDTO(blogChild.getUser_id(), null, blogChild.getBlog_text(), tmp_blog_child_images);
        }

        this.blog_count = new BlogCountDTO(blogCount);
        // 原先的设计是只返回一级评论。当点击某条评论的时候再获取二级评论。
//        this.blog_comments = blogComments;
    }
    public BlogDTO(Blog blog) {
        User user = blog.getUser();
        this.setNickname(user.getNickname());
        this.setAvatar_path(user.getAvatar_path());
        this.setBlog_id(blog.getBlog_id());
        this.setBlog_time(blog.getBlog_time());
        this.setBlog_type(blog.getBlog_type());
        List<String> imagesList = null;
        if (blog.getBlogImages() != null) {
            imagesList = new ArrayList<>();
            for (BlogImage blogImage : blog.getBlogImages()) {
                imagesList.add(blogImage.getBlog_image());
            }
        }
        this.blog_content = new BlogContentDTO(blog.getBlog_text(),imagesList);
        this.blog_count = new BlogCountDTO(blog.getBlogCount());
        if (blog.getBlog_type() > 0) {
            this.blog_child = new BlogChildDTO(blog.getReply());
        }
    }

//    public List<BlogDTO> convertToList(List<Blog> blogs) {
//        List<BlogDTO> blogDTOList = new ArrayList<>();
//        int i = 0;
//        for(Blog blog: blogs) {
//            System.out.println(i);
//            blogDTOList.add(new BlogDTO(blog));
//        }
//        return blogDTOList;
//    }
}

