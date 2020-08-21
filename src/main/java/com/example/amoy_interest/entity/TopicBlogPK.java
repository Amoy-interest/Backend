package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: Mok
 * @Date: 2020/8/19 9:04
 */
@Data
public class TopicBlogPK implements Serializable {
    @Id
    private Integer topic_id;
    @Id
    private Integer blog_id;
}
