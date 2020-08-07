package com.example.amoy_interest.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_info")
public class User {
    @Id
    private Integer user_id;
    private String nickname;
    private String email;
    private Integer sex;
    private String address;
    private Integer credits;
    private String introduction;
    private String avatar_path;
    public User(Integer user_id,String nickname,String email, Integer sex, String address, Integer credits, String introduction, String avatar_path) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.sex = sex;
        this.address = address;
        this.credits = credits;
        this.introduction = introduction;
        this.avatar_path = avatar_path;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return user_id == that.user_id &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(address, that.address) &&
                Objects.equals(credits,that.credits) &&
                Objects.equals(introduction,that.introduction) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, nickname,address,email,sex,address,credits,introduction,avatar_path);
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserAuth userAuth;
//    @OneToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
//    private UserCount userCount;
    //一般用分页，不再关联
//    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//    private List<Blog> blogs;
//    @OneToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
//    private UserBan userBan;
    //这个是不是也得不再关联？
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<UserFollow> userFollow;

}
