package com.example.demo.dto;

import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogImage;
import com.example.demo.entity.Topic;
import com.example.demo.entity.User;
import io.swagger.annotations.Api;
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
    @ApiModelProperty(value = "该话题的所有博文", required = false)
    private List<BlogDTO> blogs;
    @ApiModelProperty(value = "话题的标题", required = true)
    private String name;
    @ApiModelProperty(value = "话题创建时间", required = true)
    private Date time;

    public TopicDTO(Topic topic) {
        this.name = topic.getTopic_name();
        this.time = topic.getTopic_time();
//        this.blogs = topic.getBlogs();
    }
//    public static class Topic_Blog {
//        private String nickname;
//        private String avatar_path;
//        private Integer blog_type;
//        private Date blog_time;
//        private BlogContentDTO blog_content;
//        private BlogChildDTO blog_child;
//        private BlogCountDTO blog_count;
//        public List<Topic_Blog> convert(List<Blog> blogs) {
//            List<Topic_Blog> list = new ArrayList<>();
//            for(Blog blog : blogs) {
//                Topic_Blog topic_blog = new Topic_Blog();
//                User user = blog.getUser();
//                topic_blog.nickname = user.getNickname();
//                topic_blog.avatar_path = user.getAvatar_path();
//                topic_blog.blog_type = blog.getBlog_type();
//                topic_blog.blog_time = blog.getBlog_time();
//                topic_blog.blog_content.setText(blog.getBlog_text());
//                List<BlogImage> blogImages = blog.getBlogImages();
//                List<String> tmp_blog_images = new ArrayList<>();
//                for (BlogImage blogImage : blogImages) {
//                    tmp_blog_images.add(blogImage.getBlog_image());
//                }
//                topic_blog.blog_content.setImages(tmp_blog_images);
////                if(blog.getBlog_type() > 0) {
////                    this.blog_child.setText(blogChild.getBlog_text());
////                    List<String> tmp_blog_child_images = new ArrayList<>();
////                    for (BlogImage blogImage : blogChildImages) {
////                        tmp_blog_child_images.add(blogImage.getBlog_image());
////                    }
////                    this.blog_child.setImages(tmp_blog_child_images);
////                }
//            }
//        }
//    }
}
