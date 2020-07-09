package com.example.demo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    private int user_id;
    private String email;
    private int sex;
    private String address;
    private int credits;
    private String introduction;
    private String avatar_path;
}
