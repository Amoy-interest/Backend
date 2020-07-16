package com.example.amoy_interest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_info")
public class User {
    @Id
    private int user_id;
    private String nickname;
    private String email;
    private int sex;
    private String address;
    private int credits;
    private String introduction;
    private String avatar_path;
    public User(int user_id,String nickname,String email, int sex, String address, int credits, String introduction, String avatar_path) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.sex = sex;
        this.address = address;
        this.credits = credits;
        this.introduction = introduction;
        this.avatar_path = avatar_path;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserAuth userAuth;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserCount userCount;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Blog> blogs;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserBan userBan;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<UserFollow> userFollow;

}
