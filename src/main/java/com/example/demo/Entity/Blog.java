package com.example.demo.Entity;


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
    private String blog_image;

}
