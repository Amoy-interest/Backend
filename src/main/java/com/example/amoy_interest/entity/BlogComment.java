package com.example.amoy_interest.entity;

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
    private String nickname;
    private String reply_comment_nickname;
    private int comment_level;
    private String comment_text;
    private Date comment_time;
    private int vote_count;
    private boolean is_deleted;
    private int root_comment_id;

    public BlogComment() {}
    public BlogComment(int comment_id, int blog_id, String nickname, String reply_comment_nickname, int comment_level, String comment_text, Date comment_time,
                       int vote_count, boolean is_deleted, int root_comment_id) {
        this.comment_id = comment_id;
        this.blog_id = blog_id;
        this.nickname = nickname;
        this.reply_comment_nickname = reply_comment_nickname;
        this.comment_level = comment_level;
        this.comment_text = comment_text;
        this.comment_time = comment_time;
        this.vote_count = vote_count;
        this.is_deleted = is_deleted;
        this.root_comment_id = root_comment_id;
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="blog_id",insertable = false, updatable = false)
//    private Blog blog;

}
