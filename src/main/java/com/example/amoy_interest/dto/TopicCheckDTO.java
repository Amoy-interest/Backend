package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicCheckDTO", description = "点赞信息")
public class TopicCheckDTO {
    @ApiModelProperty(value = "审核的topic名",example = "高考加油",required = true)
    private String topic_name;
    @ApiModelProperty(value = "审核状态(0是未审核，1是审核通过，2是审核删除)",example = "1",required = true)
    private Integer check_status;
}
