package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/3 10:16
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "UserForbiddenResult", description = "用户封号信息")
public class UserForbiddenResult {
    @ApiModelProperty(value = "被封号用户的id")
    private Integer user_id;
    @ApiModelProperty(value = "被封号用户的昵称")
    private String nickname;
    @ApiModelProperty(value = "封号结束时间")
    private Date forbidden_time;
}
