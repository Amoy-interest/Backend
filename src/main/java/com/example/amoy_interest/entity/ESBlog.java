package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/10 11:19
 */
@Document(indexName = "blog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ESBlog implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Integer id;
    private Integer user_id;
    private Integer topic_id;
    private Integer blog_type;
    private Date blog_time;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String blog_text;
    private boolean is_deleted;
    private Integer check_status;
    private Integer reply_blog_id;
    public ESBlog(Blog blog) {
        this.id = blog.getBlog_id();
        this.user_id = blog.getUser_id();
        this.topic_id = blog.getTopic_id();
        this.blog_type = blog.getBlog_type();
        this.blog_text = blog.getBlog_text();
        this.blog_time = blog.getBlog_time();
        this.is_deleted = blog.is_deleted();
        this.check_status = blog.getCheck_status();
        this.reply_blog_id = blog.getReply().getBlog_id();
    }
}
