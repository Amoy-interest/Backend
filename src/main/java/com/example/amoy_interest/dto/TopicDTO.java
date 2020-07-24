package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicDto", description = "话题信息")
public class TopicDTO {
//    @ApiModelProperty(value = "该话题的所有博文", required = false)
//    private List<BlogDTO> blogs;
    @ApiModelProperty(value = "话题的标题", required = true)
    private String name;
    @ApiModelProperty(value = "话题创建时间", required = true)
    private Date time;
    @ApiModelProperty(value = "主持人id")
    private int host_id;
    @ApiModelProperty(value = "话题logo")
    private String logo_path;
    @ApiModelProperty(value = "话题简介")
    private String topic_intro;
    @ApiModelProperty(value = "话题热度")
    private Integer topic_heat;

    public TopicDTO(Topic topic) {
//        BlogDTO convert = null;
        this.name = topic.getTopic_name();
        this.time = topic.getTopic_time();
        this.host_id = topic.getHost_id();
        this.logo_path = topic.getLogo_path();
        this.topic_intro = topic.getTopic_intro();
        if(topic.getTopicHeat() == null) {
            this.topic_heat = 0;
        }else {
            this.topic_heat = topic.getTopicHeat().getHeat();
        }
//        this.blogs = null;
//        if(topic.getBlogs() != null) {
//            this.blogs = new ArrayList<>();
//            for(Blog blog: topic.getBlogs()) {
//                this.blogs.add(new BlogDTO(blog));
//            }
//        }
    }
//    @ApiModel(value = "Topic_Blog", description = "话题下的博文信息")
//    public static class Topic_Blog {
//        @ApiModelProperty(value = "发此博文的用户的昵称",example = "mok")
//        private String nickname;
//        @ApiModelProperty(value = "此博文的类型(0为原创，1为转发)")
//        private Integer blog_type;
//        @ApiModelProperty(value = "博文发布时间")
//        private Date blog_time;
//        @ApiModelProperty(value = "博文的内容")
//        private BlogContentDTO blog_content;
//        @ApiModelProperty(value = "博文为转发时的转发对象")
//        private BlogChildDTO blog_child;
//        @ApiModelProperty(value = "博文计数信息")
//        private BlogCountDTO blog_count;
//        @ApiModelProperty(value = "用户头像")
//        private String avatar_path;
//    }
//    public List<Topic_Blog> convert(List<Blog> blogs) {
//        List<Topic_Blog> list = new ArrayList<>();
//        for(Blog blog : blogs) {
//            if(blog.is_deleted() || blog.getCheck_status()==2) continue;//过滤掉被删除的blog
//            Topic_Blog topic_blog = new Topic_Blog();
//            User user = blog.getUser();
//            topic_blog.nickname = user.getNickname();
//            topic_blog.avatar_path = user.getAvatar_path();
//            topic_blog.blog_type = blog.getBlog_type();
//            topic_blog.blog_time = blog.getBlog_time();
//            List<BlogImage> blogImages = blog.getBlogImages();
//            List<String> tmp_blog_images = new ArrayList<>();
//            if(blogImages != null) {
//                for (BlogImage blogImage : blogImages) {
//                    tmp_blog_images.add(blogImage.getBlog_image());
//                }
//            }
//            topic_blog.blog_content = new BlogContentDTO(blog.getBlog_text(), tmp_blog_images);
//            topic_blog.blog_child = null;  //转发还在topic里吗？？暂时不在
//            topic_blog.blog_count = new BlogCountDTO(blog.getBlogCount());
////                blog.get
////                if(blog.getBlog_type() > 0) {
////                    this.blog_child.setText(blogChild.getBlog_text());
////                    List<String> tmp_blog_child_images = new ArrayList<>();
////                    for (BlogImage blogImage : blogChildImages) {
////                        tmp_blog_child_images.add(blogImage.getBlog_image());
////                    }
////                    this.blog_child.setImages(tmp_blog_child_images);
////                }
//            list.add(topic_blog);
//        }
//        return list;
//    }
}
