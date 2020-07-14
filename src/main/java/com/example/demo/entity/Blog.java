package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_id",insertable = false, updatable = false)
    private Topic topic;
//    @OneToMany(fetch = FetchType.LAZY)
//
}
