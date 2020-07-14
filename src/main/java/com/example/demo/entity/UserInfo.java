package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
