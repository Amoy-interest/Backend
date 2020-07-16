package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogChild", description = "转发的博文信息")
public class BlogChildDTO {
    @ApiModelProperty(value = "转发的用户id",example = "1",required = true)
    private int user_id;
    @ApiModelProperty(value = "转发的用户昵称",example = "mok",required = true)
    private String nickname;
    @ApiModelProperty(value = "转发的内容", example = "早上好！",required = true)
    private String text;
    @ApiModelProperty(value = "转发内容中的图片")
    private List<String> images;
}
