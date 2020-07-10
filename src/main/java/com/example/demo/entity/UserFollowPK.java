package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
@Data
public class UserFollowPK implements Serializable {
    @Id
    private int user_id;
    @Id
    private int follow_id;
}
