package com.example.demo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blog_comment")
public class UserFollow {
    @Id
    private int user_id;
    @Id
    private int follow_id;
}
