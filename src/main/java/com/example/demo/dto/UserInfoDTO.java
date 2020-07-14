package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
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
@ApiModel(value = "User", description = "用户基本信息")
public class UserInfoDTO{
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "性别", required = true,example = "0代表女性，1代表男性")
    private int sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
//    @ApiModelProperty(value = "信用值", required = true)
//    private int credits;
    @ApiModelProperty(value = "个人简介", required = true)
    private String introduction;
    @ApiModelProperty(value = "个人头像", required = true)
    private String avatar;

    public UserInfoDTO (User user, UserInfo userInfo) {
        this.username = user.getUsername();
        this.sex = userInfo.getSex();
        this.address = userInfo.getAddress();
        this.introduction = userInfo.getIntroduction();
        this.avatar = userInfo.getAvatar_path();
    }
}
