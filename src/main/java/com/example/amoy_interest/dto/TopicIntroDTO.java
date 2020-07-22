package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicLogoDTO", description = "话题logo信息")
public class TopicIntroDTO {
    @ApiModelProperty(value = "话题的名字", required = true)
    private String topic_name;
    @ApiModelProperty(value = "话题的简介", required = true)
    private String topic_intro;
}
