package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserInfoDTO", description = "用户基本信息")
public class UserInfoDTO{
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;
    @ApiModelProperty(value = "性别(0代表女性，1代表男性)",example = "0",required = true)
    private Integer sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
//    @ApiModelProperty(value = "信用值", required = true)
//    private int credits;
    @ApiModelProperty(value = "个人简介", required = true)
    private String introduction;
    @ApiModelProperty(value = "个人头像", required = true)
    private String avatar;
    @ApiModelProperty(value = "用户类型")
    private int user_type;

    public UserInfoDTO (User user) {
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.address = user.getAddress();
        this.introduction = user.getIntroduction();
        this.avatar = user.getAvatar_path();
        this.user_type = user.getUserAuth().getUser_type();
    }
}