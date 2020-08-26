package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sim_blog")
@IdClass(SimBlogPK.class)
public class SimBlog {
    @Id
    private int blog_id;
    @Id
    private int sim_id;
}
