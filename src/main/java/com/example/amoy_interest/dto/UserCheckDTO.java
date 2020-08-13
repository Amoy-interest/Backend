package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserCheckDTO", description = "用户禁言/封号信息")
public class UserCheckDTO {
    @NotNull(message = "用户id不能为空")
    @Min(value = 1,message = "用户id不能小于1")
    @ApiModelProperty(value = "禁言/封号用户id",example = "1", required = true)
    private Integer user_id;
    @NotNull(message = "封号秒数不能为空")
    @Min(value = 1,message = "封号时长不能小于1秒")
    @ApiModelProperty(value = "禁言/封号时长(秒数)",example = "86400",required = true)
    private Long time;
}
