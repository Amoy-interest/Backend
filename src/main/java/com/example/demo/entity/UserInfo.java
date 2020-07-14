package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
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
    public UserInfo(int user_id,String email,int sex,String address,int credits,String introduction,String avatar_path) {
        this.user_id = user_id;
        this.email = email;
        this.sex = sex;
        this.address = address;
        this.credits = credits;
        this.introduction = introduction;
        this.avatar_path = avatar_path;
    }

//    @OneToOne(mappedBy = "userInfo",cascade= CascadeType.ALL,fetch=FetchType.LAZY)
//    private User user;
}
