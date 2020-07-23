package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.Blog;
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
@ApiModel(value = "BlogChild", description = "转发的博文信息")
public class BlogChildDTO {
    @ApiModelProperty(value = "转发的博文id")
    private int blog_id;
    @ApiModelProperty(value = "转发的用户id", example = "1", required = true)
    private int user_id;
    @ApiModelProperty(value = "转发的用户昵称", example = "mok", required = true)
    private String nickname;
    @ApiModelProperty(value = "转发的用户头像")
    private String avatar_path;
    @ApiModelProperty(value = "转发的博文的内容")
    private BlogContentDTO blog_content;
    @ApiModelProperty(value = "转发的博文的发博时间")
    private Date blog_time;

    public BlogChildDTO(Blog child) {
        this.blog_content = new BlogContentDTO(child);
        this.avatar_path = child.getUser().getAvatar_path();
        this.user_id = child.getUser_id();
        this.nickname = child.getUser().getNickname();
        this.blog_id = child.getBlog_id();
        this.blog_time = child.getBlog_time();
    }
}
