package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicCheckDTO", description = "点赞信息")
public class TopicCheckDTO {
    @NotNull(message = "审核的话题名不能为空")
    @NotBlank(message = "审核的话题名不能为空白")
    @Length(max = 40,message = "话题名不能大于40位")
    @ApiModelProperty(value = "审核的topic名",example = "高考加油",required = true)
    private String topic_name;
    @NotNull(message = "审核码不能为空")
    @Range(min = 1,max = 2,message = "审核码只能在1或2")
    @ApiModelProperty(value = "审核状态(0是未审核，1是审核通过，2是审核删除)",example = "1",required = true)
    private Integer check_status;
}
