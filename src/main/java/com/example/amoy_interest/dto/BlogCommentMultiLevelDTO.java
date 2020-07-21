package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.BlogComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogCommentMultiLevelDTO", description = "子评论信息")
public class BlogCommentMultiLevelDTO {
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

    public BlogCommentMultiLevelDTO(BlogComment blogComment) {

    }
}
