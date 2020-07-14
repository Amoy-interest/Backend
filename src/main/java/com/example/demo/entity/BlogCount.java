package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
