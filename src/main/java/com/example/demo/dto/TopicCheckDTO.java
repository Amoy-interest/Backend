package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "点赞信息", description = "点赞信息")
public class TopicCheckDTO {
    private String topic_name;
    private Integer check_status;
}