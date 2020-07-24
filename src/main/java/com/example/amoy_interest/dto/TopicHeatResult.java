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
@ApiModel(value = "TopicHeatResult", description = "话题热度信息")
public class TopicHeatResult {
    @ApiModelProperty(value = "话题的标题", required = true)
    private String topic_name;
    @ApiModelProperty(value = "话题热度")
    private Integer heat;
}
