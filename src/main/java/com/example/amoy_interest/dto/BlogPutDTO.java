package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "编辑博文信息", description = "编辑博文信息")
public class BlogPutDTO {
    private Integer blog_id;
    private String text;
}
