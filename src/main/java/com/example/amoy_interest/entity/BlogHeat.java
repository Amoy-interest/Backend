package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Mok
 * @Date: 2020/8/24 17:46
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog_heat")
public class BlogHeat {
    @Id
    private Integer blog_id;
    private Integer heat;

    public BlogHeat(BlogHeat bh) {
        this.blog_id = bh.blog_id;
        this.heat = bh.heat;
    }
}
