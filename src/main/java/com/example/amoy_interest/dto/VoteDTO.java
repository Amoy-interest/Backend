package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "VoteDTO", description = "点赞信息")
public class VoteDTO {
    @NotNull(message = "博文id不能为空")
    @Min(value = 0,message = "博文id不能小于0")
    @ApiModelProperty(value = "博文id(对评论点赞则将blog_id设为0)",example = "0",required = true)
    private Integer blog_id;
    @NotNull(message = "评论id不能为空")
    @Min(value = 0,message = "评论id不能小于0")
    @ApiModelProperty(value = "评论id(对博文点赞则设为0)",example = "0",required = true)
    private Integer comment_id;
}
