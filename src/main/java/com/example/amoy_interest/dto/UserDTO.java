package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserDto", description = "登录/注册返回信息")
public class UserDTO {
    private UserInfoDTO user;
    private String token;
}
