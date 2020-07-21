package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "BlogCheckDTO", description = "博文审核信息")
public class BlogCheckDTO {
    @ApiModelProperty(value = "审核的博文id",example = "1",required = true)
    private Integer blog_id;
    @ApiModelProperty(value = "审核状态(0是未审核，1是审核通过，2是审核删除)",example = "1",required = true)
    private Integer check_status;
}
