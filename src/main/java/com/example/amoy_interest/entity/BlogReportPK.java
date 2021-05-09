package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: Mok
 * @Date: 2020/8/20 11:21
 */
@Data
public class BlogReportPK implements Serializable {
    @Id
    private Integer blog_id;
    @Id
    private Integer user_id;
}
