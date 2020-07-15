package com.example.demo.dto;

import com.example.demo.msgutils.Msg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserDto", description = "登录/注册返回信息")
public class UserDTO {
    private UserInfoDTO user;
    private String token;
}
