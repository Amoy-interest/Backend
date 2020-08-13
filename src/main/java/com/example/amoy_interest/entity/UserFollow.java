package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
