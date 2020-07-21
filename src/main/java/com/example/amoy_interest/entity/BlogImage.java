package com.example.amoy_interest.entity;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "blog_image")
public class BlogImage {
    @Id
    private int blog_id;

    private String blog_image;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="blog_id",insertable = false, updatable = false)
//    private Blog blog;
    public BlogImage() {}
    public BlogImage(int blog_id, String blog_image) {
        this.blog_id = blog_id;
        this.blog_image = blog_image;
    }
}
