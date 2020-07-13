package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogCountDto", description = "博文计数信息")
public class BlogCountDTO {
    private Integer forward_count;
    private Integer comment_count;
    private Integer vote_count;
    private Integer report_count;
}
