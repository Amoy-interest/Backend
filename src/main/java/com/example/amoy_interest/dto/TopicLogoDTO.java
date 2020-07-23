package com.example.amoy_interest.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@ApiModel(value = "TopicLogoDTO", description = "话题logo信息")
public class TopicLogoDTO {
    @NotNull(message = "话题名不能为空")
    @NotBlank(message = "话题名不能为空白")
    @Length(max = 40,message = "话题名不能大于40位")
    @ApiModelProperty(value = "话题的名字", required = true)
    private String topic_name;
    @NotNull(message = "话题logo不能为空")
    @NotEmpty(message = "话题logo不能为空字符串")
    @Length(max = 1024,message = "logo不能大于1024位")
    @ApiModelProperty(value = "话题logo", required = true)
    private String logo_path;
}
