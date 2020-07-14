package com.example.demo.entity;

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
}
