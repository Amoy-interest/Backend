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
    private Integer topic_id;
    private String topic_name;
    private Date topic_time;
    private Integer check_status;
    private Integer report_count;
    private Integer host_id;
    private String logo_path;
    private String topic_intro;

    public Topic(String topic_name,Date topic_time,int check_status,int report_count,Integer host_id,String logo_path,String topic_intro){
        this.topic_name = topic_name;
        this.topic_time = topic_time;
        this.check_status = check_status;
        this.report_count = report_count;
        this.host_id = host_id;
        this.logo_path = logo_path;
        this.topic_intro = topic_intro;
    }
//    @OneToMany(fetch=FetchType.LAZY)
//    @JoinColumn(name = "topic_id",referencedColumnName = "topic_id")
//    private List<Blog> blogs;
}
