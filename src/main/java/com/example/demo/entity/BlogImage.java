package com.example.demo.entity;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blog_image")
public class BlogImage {
    @Id
    private int blog_id;

    private String blog_image;
}
