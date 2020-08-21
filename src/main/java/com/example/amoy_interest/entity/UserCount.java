package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_count")
public class UserCount {
    @Id
    private int user_id;

    private int follow_count;
    private int fan_count;
    private int blog_count;
    private int report_count;
//    public UserCount(int user_id,int follow_count,int fan_count,int blog_count) {
//        this.user_id = user_id;
//        this.follow_count = follow_count;
//        this.fan_count = fan_count;
//        this.blog_count = blog_count;
//    }

}
