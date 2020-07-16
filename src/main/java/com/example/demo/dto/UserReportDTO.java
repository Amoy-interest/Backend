package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "被举报用户信息", description = "被举报用户信息")
public class UserReportDTO {
    private Integer user_id;
    private String username;
    private Integer credits;
    //是否需要举报原因？
}
