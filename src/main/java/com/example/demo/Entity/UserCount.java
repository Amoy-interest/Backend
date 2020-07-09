package com.example.demo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_count")
public class UserCount {
    @Id
    private int user_id;

    private int follow_count;
    private int fan_count;
    private int blog_count;
}
