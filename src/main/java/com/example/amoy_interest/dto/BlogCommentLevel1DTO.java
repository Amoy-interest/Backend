package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogCommentLevel1DTO", description = "一级评论信息")
public class BlogCommentLevel1DTO {
    @ApiModelProperty(value = "该评论的用户id", required = true)
    private Integer user_id;
    @ApiModelProperty(value = "该评论的用户名", required = true)
    private String nickname;
    @ApiModelProperty(value = "该评论的评论id", required = true)
    private Integer comment_id;
    @ApiModelProperty(value = "该评论所在的博文id", required = true)
    private Integer blog_id;
    @ApiModelProperty(value = "该评论的文字内容", required = true)
    private String comment_text;
    @ApiModelProperty(value = "评论时间", required = true)
    private Date comment_time;
    @ApiModelProperty(value = "评论点赞数", required = true)
    private Integer vote_count;
    @ApiModelProperty(value = "是否有子评论")
    private boolean have_child;
}
