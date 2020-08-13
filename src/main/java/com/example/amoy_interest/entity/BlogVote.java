package com.example.amoy_interest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: Mok
 * @Date: 2020/7/29 14:58
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "blog_vote")
public class BlogVote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer blog_id;
    private Integer user_id;
    private Integer status;
    public BlogVote(Integer blog_id,Integer user_id,Integer status) {
        this.blog_id = blog_id;
        this.user_id = user_id;
        this.status = status;
    }
}
