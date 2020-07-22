package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topic_id;
    private String topic_name;
    private Date topic_time;
    private int check_status;
    private int report_count;

//    public Topic(int topic_id,String topic_name,Date topic_time,int check_status,int report_count){
//        this.topic_id = topic_id;
//        this.topic_name = topic_name;
//        this.topic_time = topic_time;
//        this.check_status = check_status;
//        this.report_count = report_count;
//    }
//    @OneToMany(fetch=FetchType.LAZY)
//    @JoinColumn(name = "topic_id",referencedColumnName = "topic_id")
//    private List<Blog> blogs;
}
