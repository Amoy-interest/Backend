package com.example.amoy_interest.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "user_id")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String username;
    private String password;
    private int user_type;
    private int is_ban; //用户禁言
    private int is_forbidden;     // 封号


    public UserAuth(String username, String password, int user_type, int is_ban,int is_forbidden) {
        this.user_type = user_type;
        this.username = username;
        this.password = password;
        this.is_ban = is_ban;
        this.is_forbidden = is_forbidden;
    }
    public UserAuth(int user_id, String username, String password, int user_type, int is_ban,int is_forbidden) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.is_ban = is_ban;
        this.is_forbidden = is_forbidden;
        this.username = username;
        this.password = password;
    }
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
