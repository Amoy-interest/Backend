package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_ban")
public class UserBan {
    @Id
    private int user_id;
    private Date ban_time;
    private Date forbidden_time;
}
