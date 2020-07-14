package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_follow")
@IdClass(UserFollowPK.class)
public class UserFollow {
    @Id
    private int user_id;
    @Id
    private int follow_id;
}
