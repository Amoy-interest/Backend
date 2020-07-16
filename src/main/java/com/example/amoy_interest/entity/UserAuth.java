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
    private int user_status; //用户禁言或者封号状态

    public UserAuth(String username, String password, int user_type, int user_status) {
        this.user_type = user_type;
        this.user_status = user_status;
        this.username = username;
        this.password = password;
    }
    public UserAuth(int user_id, String username, String password, int user_type, int user_status) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.user_status = user_status;
        this.username = username;
        this.password = password;
    }
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
