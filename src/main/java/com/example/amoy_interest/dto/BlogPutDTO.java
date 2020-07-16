package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogPutDTO", description = "编辑博文信息")
public class BlogPutDTO {
    @ApiModelProperty(value = "编辑的博文id",required = true)
    private Integer blog_id;
    @ApiModelProperty(value = "编辑的博文内容(不支持编辑图片)",required = true)
    private String text;
}
