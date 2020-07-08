package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogCommentDto", description = "博文评论信息")
public class BlogCommentDTO {
    private Integer user_id;
    private String username;
    private Integer comment_id;
    private String text;
    private List<BlogCommentDTO> comment_child;
}
