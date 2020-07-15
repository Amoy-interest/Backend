package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "用户禁言/封号信息", description = "用户禁言/封号信息")
public class UserCheckDTO {
    private Integer user_id;
    private Date time;
}
