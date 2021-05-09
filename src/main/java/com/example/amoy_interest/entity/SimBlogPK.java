package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SimBlogPK implements Serializable {
    @Id
    private int blog_id;
    @Id
    private int sim_id;
}
