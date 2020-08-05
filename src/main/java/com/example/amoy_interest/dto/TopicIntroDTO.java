package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "TopicIntroDTO", description = "话题logo信息")
public class TopicIntroDTO {
    @NotNull(message = "话题名不能为空")
    @NotBlank(message = "话题名不能为空白")
    @Length(max = 40,message = "话题名不能大于40位")
    @ApiModelProperty(value = "话题的名字", required = true)
    private String topic_name;
    @NotNull(message = "话题简介不能为空")
    @NotBlank(message = "话题简介不能为空白")
    @Length(max = 140,message = "话题简介不能大于140位")
    @ApiModelProperty(value = "话题的简介", required = true)
    private String topic_intro;
}
