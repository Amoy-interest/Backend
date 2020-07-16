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
@ApiModel(value = "CommentPostDTO", description = "新建评论信息")
public class CommentPostDTO {
    private Integer blog_id;
    private Integer root_comment_id;
    private String nickname;
    private String reply_comment_nickname;
    private String text;
}
