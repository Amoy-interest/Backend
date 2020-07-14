package com.example.demo.entity;


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
    private Integer reply_blog_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",insertable = false, updatable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="topic_id",insertable = false, updatable = false)
//    private Topic topic;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
    private List<BlogComment> comments;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
    private BlogCount blogCount;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
    private List<BlogImage> blogImages;
}
