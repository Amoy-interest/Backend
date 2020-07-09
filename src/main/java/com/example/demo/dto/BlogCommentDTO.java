package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
@ApiModel(value = "BlogCommentDto", description = "博文评论信息")
public class BlogCommentDTO {
    @ApiModelProperty(value = "该评论的用户id", required = true)
    private Integer user_id;
    @ApiModelProperty(value = "该评论的用户名", required = true)
    private String username;
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
    @ApiModelProperty(value = "多级评论下，该评论的根评论的id", required = false)
    private Integer root_comment_id;
    @ApiModelProperty(value = "回复评论，所回复的用户名", required = false)
    private String reply_comment_username;
    @ApiModelProperty(value = "根评论的所有子评论", required = false)
    private List<BlogCommentDTO> comment_child;
}
