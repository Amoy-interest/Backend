package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "新建评论信息", description = "新建评论信息")
public class CommentPostDTO {
    private Integer blog_id;
    private Integer root_comment_id;
    private String reply_comment_nickname;
    private String text;
}
