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
    @ApiModelProperty(value = "该评论用户头像")
    private String avatar_path;
    @ApiModelProperty(value = "该评论的评论id", required = true)
    private Integer comment_id;
    @ApiModelProperty(value = "该评论回复的用户id", required = true)
    private Integer reply_user_id;
    @ApiModelProperty(value = "该评论回复的用户昵称")
    private String reply_user_nickname;
    @ApiModelProperty(value = "该评论的文字内容", required = true)
    private String comment_text;
    @ApiModelProperty(value = "评论时间", required = true)
    private Date comment_time;
    @ApiModelProperty(value = "评论点赞数", required = true)
    private Integer vote_count;

    public BlogCommentMultiLevelDTO(BlogComment blogComment,String nickname,String reply_user_nickname,String avatar_path) {
        this.user_id = blogComment.getUser_id();
        this.comment_id = blogComment.getComment_id();
        this.reply_user_id = blogComment.getReply_user_id();
        this.comment_text = blogComment.getComment_text();
        this.comment_time = blogComment.getComment_time();
        this.vote_count = blogComment.getVote_count();
        this.nickname = nickname;
        this.avatar_path = avatar_path;
        this.reply_user_nickname = reply_user_nickname;
    }
}
