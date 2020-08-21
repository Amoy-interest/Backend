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
 * @Date: 2020/8/20 11:18
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_report")
@IdClass(UserReportPK.class)
public class UserReport {
    @Id
    private Integer user_id;
    @Id
    private Integer reporter_id;
    private String report_reason;
}
