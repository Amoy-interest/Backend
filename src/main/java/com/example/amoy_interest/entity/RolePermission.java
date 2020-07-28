package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: Mok
 * @Date: 2020/7/28 10:01
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role_name;
    private String permission;
}
