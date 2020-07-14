package com.example.demo.dto;

import com.example.demo.entity.Blog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogContentDto", description = "博文内容")
public class BlogContentDTO {
    @ApiModelProperty(value = "博文文字", required = true)
    private String text;
    @ApiModelProperty(value = "博文图片", required = false)
    private List<String> images;
}


