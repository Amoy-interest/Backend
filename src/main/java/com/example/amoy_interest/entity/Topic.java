package com.example.amoy_interest.entity;

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
    private int check_status;
    private int report_count;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "topic_id",referencedColumnName = "topic_id")
    private List<Blog> blogs;
}
