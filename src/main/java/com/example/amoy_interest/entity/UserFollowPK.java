package com.example.amoy_interest.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserFollowPK implements Serializable {
    @Id
    private int user_id;
    @Id
    private int follow_id;
}
