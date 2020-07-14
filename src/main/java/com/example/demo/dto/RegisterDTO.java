package com.example.demo.dto;

import com.example.demo.msgutils.Msg;
import io.swagger.annotations.ApiImplicitParam;
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
@ApiModel(value = "RegisterDto", description = "注册信息")
public class RegisterDTO {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "性别", required = true,example = "0代表女性，1代表男性")
    private int sex;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;
}
