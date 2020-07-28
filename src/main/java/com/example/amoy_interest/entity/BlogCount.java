package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blog_count")
public class BlogCount {
    @Id
    private int blog_id;
    private int forward_count;
    private int comment_count;
    private int vote_count;
    private int report_count;

//    @OneToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name = "blog_id",referencedColumnName = "blog_id")
//    private Blog blog;
    public BlogCount() {}
    public BlogCount(int blog_id, int forward_count, int comment_count, int vote_count, int report_count) {
        this.blog_id = blog_id;
        this.forward_count = forward_count;
        this.comment_count = comment_count;
        this.vote_count = vote_count;
        this.report_count = report_count;
    }
}
