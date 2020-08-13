package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "BlogCheckDTO", description = "博文审核信息")
public class BlogCheckDTO {
    @NotNull(message = "博文id不能为空")
    @Min(value = 1,message = "id不能小于1")
    @ApiModelProperty(value = "审核的博文id",example = "1",required = true)
    private Integer blog_id;
    @NotNull(message = "审核码不能为空")
    @Range(min = 0,max = 2,message = "审核码不正确")
    @ApiModelProperty(value = "审核状态(0是未审核，1是审核通过，2是审核删除)",example = "1",required = true)
    private Integer check_status;
}
