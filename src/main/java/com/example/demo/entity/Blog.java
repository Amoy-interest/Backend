package com.example.demo.entity;


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
    private int blog_type;
    private Date blog_time;
    private String blog_text;
    private boolean is_deleted;
    private Integer check_status;
    private Integer topic_id;
    private Integer reply_blog_id;
}
