package com.example.demo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "被举报话题信息", description = "被举报话题信息")
public class TopicReportDTO {
    @ApiModelProperty(value = "话题的标题", required = true)
    private String name;
    @ApiModelProperty(value = "话题创建时间", required = true)
    private Date time;
    @ApiModelProperty(value = "被举报数", required = true)
    private Integer report_count;
}
