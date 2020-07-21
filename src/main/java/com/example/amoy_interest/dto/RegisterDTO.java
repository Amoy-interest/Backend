package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "RegisterDto", description = "注册信息")
public class RegisterDTO {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "性别(0表示女性，1表示男性)", example = "0",required = true)
    private Integer sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;
}
