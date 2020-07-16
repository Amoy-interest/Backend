package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "点赞信息", description = "点赞信息")
public class VoteDTO {
    @ApiModelProperty(value = "博文id(对评论点赞则将blog_id设为-1)",example = "0",required = true)
    private Integer blog_id;
    @ApiModelProperty(value = "评论id",example = "0",required = true)
    private Integer comment_id;
}
