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
 * @Date: 2020/8/20 11:21
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog_report")
@IdClass(BlogReportPK.class)
public class BlogReport {
    @Id
    private Integer blog_id;
    @Id
    private Integer user_id;
    private String report_reason;

}
