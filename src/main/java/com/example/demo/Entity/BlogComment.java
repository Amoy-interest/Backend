package com.example.demo.Entity;

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
    private int root_comment_id;
    private boolean is_deleted;
    private int vote_count;
    private Date comment_time;
    private int comment_level;
    private String comment_text;
}
