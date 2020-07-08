package com.example.demo.dto;

import com.example.demo.msgutils.Msg;
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
@ApiModel(value = "UserDto", description = "登录/注册返回信息")
public class UserDTO {
    private User user;
    private Msg msg;
}
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "User", description = "用户信息")
class User{
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "性别", required = true,example = "0代表女性，1代表男性")
    private String sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
}
