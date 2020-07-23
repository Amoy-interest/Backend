package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "LoginDto", description = "登录信息")
public class LoginDTO {
    @NotBlank(message = "用户名不能为空白")
    @Length(max = 20,message = "用户名不能大于20位")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空字符串")
    @Length(min = 6,max = 20,message = "密码不能小于6位，不能大于20位")
    @Pattern(regexp = "^[0-9a-zA-Z_]{6,20}$",message = "密码格式错误")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
