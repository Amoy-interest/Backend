package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserCheckDTO", description = "用户禁言/封号信息")
public class UserCheckDTO {
    @ApiModelProperty(value = "禁言/封号用户id",example = "1", required = true)
    private Integer user_id;
    @ApiModelProperty(value = "禁言/封号时长(秒数)",example = "86400",required = true)
    private Long time;
}
