package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "SimUserDTO", description = "相似用户")
public class SimUserDTO {
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;
    @ApiModelProperty(value = "个人头像", required = true)
    private String avatar;
}
