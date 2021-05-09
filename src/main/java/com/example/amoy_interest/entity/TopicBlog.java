package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @Author: Mok
 * @Date: 2020/8/19 9:02
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topic_blog")
@IdClass(TopicBlogPK.class)
public class TopicBlog {
    @Id
    private Integer topic_id;
    @Id
    private Integer blog_id;
    private String topic_name;
}
