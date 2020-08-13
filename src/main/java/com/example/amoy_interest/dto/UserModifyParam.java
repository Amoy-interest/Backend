package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/3 11:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "UserModifyParam", description = "编辑用户信息")
public class UserModifyParam {
    @ApiModelProperty(value = "用户的id(从token取)",required = false)
    private Integer user_id;
    @Range(min = 0,max = 1)
    @ApiModelProperty(value = "性别",required = true)
    private Integer sex;
    @NotNull(message = "地址不能为空")
    @NotBlank(message = "地址不能为空白")
    @Length(max = 20,message = "地址不能超过20位")
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @NotNull(message = "个人简介不能为空")
    @Length(max = 20,message = "个人不能超过50位")
    @ApiModelProperty(value = "个人简介", required = true)
    private String introduction;
    @Length(max = 1024,message = "头像url不能超过1024位")
    @ApiModelProperty(value = "个人头像", required = false)
    private String avatar;
}
