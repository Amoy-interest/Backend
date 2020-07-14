package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "blog_comment")
public class BlogComment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int comment_id;
    private int blog_id;
    private String username;
    private String reply_comment_username;
    private int comment_level;
    private String comment_text;
    private Date comment_time;
    private int vote_count;
    private boolean is_deleted;
    private int root_comment_id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="blog_id",insertable = false, updatable = false)
//    private Blog blog;

}
