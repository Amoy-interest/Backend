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
 * @Date: 2020/8/3 10:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "UserBanResult", description = "用户禁言信息")
public class UserBanResult {
    @ApiModelProperty(value = "被禁言用户的id")
    private Integer user_id;
    @ApiModelProperty(value = "被禁言用户的昵称")
    private String nickname;
    @ApiModelProperty(value = "禁言结束时间")
    private Date ban_time;
}
