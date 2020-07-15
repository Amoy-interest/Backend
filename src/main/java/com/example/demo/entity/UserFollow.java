package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_follow")
@IdClass(UserFollowPK.class)
public class UserFollow {
    @Id
    private int user_id;
    @Id
    private int follow_id;
}
