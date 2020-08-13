package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "RegisterDto", description = "注册信息")
public class RegisterDTO {
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空白")
    @Length(max = 20,message = "用户名不能大于20位")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotNull(message = "昵称不能为空")
    @NotBlank(message = "昵称不能为空白")
    @Length(max = 20,message = "昵称不能大于20位")
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空字符串")
    @Length(min = 6,max = 20,message = "密码不能小于6位，不能大于20位")
    @Pattern(regexp = "^[0-9a-zA-Z_]{6,20}$")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @NotNull(message = "性别不能为空")
    @Range(min = 0,max = 1,message = "性别只能为0或1")
    @ApiModelProperty(value = "性别(0表示女性，1表示男性)", example = "0",required = true)
    private Integer sex;
    @NotNull(message = "地址不能为空")
    @NotBlank(message = "地址不能为空白")
    @Length(max = 20,message = "地址不能超过20位")
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    @ApiModelProperty(value = "邮箱",required = true)
    private String email;
}
