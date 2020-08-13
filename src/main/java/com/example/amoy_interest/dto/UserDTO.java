package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserDto", description = "用户信息")
public class UserDTO {
    @ApiModelProperty(value = "用户资料信息",required = true)
    private UserInfoDTO user;
    @ApiModelProperty(value = "用户计数信息",required = true)
    private UserCountDTO userCount;
}
