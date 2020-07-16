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
@ApiModel(value = "CommentPostDTO", description = "新建评论信息")
public class CommentPostDTO {
    @ApiModelProperty(value = "评论所在的blog的id",required = true)
    private Integer blog_id;
    @ApiModelProperty(value = "多级评论的根评论id(如果本身是一级评论，则为-1)",required =  true)
    private Integer root_comment_id;
    @ApiModelProperty(value = "评论者的昵称",required = true)
    private String nickname;
    @ApiModelProperty(value = "被评论者的昵称(多级评论的时候才有,一级评论为null)",required = false)
    private String reply_comment_nickname;
    @ApiModelProperty(value = "评论内容",required = true)
    private String text;
}
