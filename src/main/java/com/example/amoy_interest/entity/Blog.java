package com.example.amoy_interest.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int blog_id;
    private int user_id;
    private int topic_id;
    private int blog_type;
    private Date blog_time;
    private String blog_text;
    private boolean is_deleted;
    private Integer check_status;
//    private Integer reply_blog_id;

    public Blog(){}

    public Blog(int blog_id, int user_id, int topic_id, int blog_type, Date blog_time, String blog_text, boolean is_deleted, Integer check_status, Integer reply_blog_id) {
        this.blog_id = blog_id;
        this.user_id = user_id;
        this.topic_id = topic_id;
        this.blog_type = blog_type;
        this.blog_time = blog_time;
        this.blog_text = blog_text;
        this.is_deleted = is_deleted;
        this.check_status = check_status;
//        this.reply_blog_id = reply_blog_id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",insertable = false, updatable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="topic_id",insertable = false, updatable = false)
//    private Topic topic;

//    @OneToMany(fetch=FetchType.LAZY)
//    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
//    private List<BlogComment> comments;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
    private BlogCount blogCount;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
    private List<BlogImage> blogImages;

    @OneToOne(fetch = FetchType.LAZY)
    private Blog reply;
}
