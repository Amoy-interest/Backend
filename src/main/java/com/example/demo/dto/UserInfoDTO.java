package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "User", description = "用户基本信息")
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

    public UserInfoDTO (User user) {
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.address = user.getAddress();
        this.introduction = user.getIntroduction();
        this.avatar = user.getAvatar_path();
    }
}
