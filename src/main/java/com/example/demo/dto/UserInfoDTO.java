package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "User", description = "用户信息")
public class UserInfoDTO{
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "性别", required = true,example = "0代表女性，1代表男性")
    private String sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @ApiModelProperty(value = "信用值", required = true)
    private int credits;
    @ApiModelProperty(value = "个人简介", required = true)
    private String introduction;
    @ApiModelProperty(value = "个人头像", required = true)
    private String avatar;
}
