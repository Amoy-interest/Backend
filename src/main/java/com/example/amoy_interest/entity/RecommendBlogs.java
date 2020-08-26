package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommend_blogs")
@IdClass(RecommendBlogsPK.class)
public class RecommendBlogs {
    @Id
    private int user_id;
    @Id
    private int blog_id;

    private boolean recommended;
}
