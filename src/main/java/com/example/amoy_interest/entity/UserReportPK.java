package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: Mok
 * @Date: 2020/8/20 11:19
 */
@Data
public class UserReportPK implements Serializable {
    @Id
    private Integer user_id;
    @Id
    private Integer reporter_id;
}
