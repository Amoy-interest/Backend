package com.example.amoy_interest.dto;


import com.example.amoy_interest.entity.Topic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicReportDTO", description = "被举报话题信息")
public class TopicReportDTO {
    @ApiModelProperty(value = "话题的标题", required = true)
    private String name;
    @ApiModelProperty(value = "话题创建时间", required = true)
    private Date time;
    @ApiModelProperty(value = "被举报数", required = true)
    private Integer report_count;

    public TopicReportDTO(Topic topic) {
        this.name = topic.getTopic_name();
        this.time = topic.getTopic_time();
        this.report_count = topic.getReport_count();
    }
}
