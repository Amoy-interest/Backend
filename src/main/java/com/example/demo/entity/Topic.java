package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topic_id;
    private String topic_name;
    private Date topic_time;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "topic_id",referencedColumnName = "topic_id")
    private List<Blog> blogs;
}
