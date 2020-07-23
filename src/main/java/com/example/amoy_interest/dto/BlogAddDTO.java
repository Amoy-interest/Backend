package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogAddDto", description = "新建博文所需内容")
public class BlogAddDTO {
    @NotBlank(message = "博文内容不能为空")
    @Length(max = 140, message = "博文内容不能大于140位")
    @ApiModelProperty(value = "博文文字", required = true)
    private String text;
    @ApiModelProperty(value = "博文图片", required = false)
    private List<String> images;
    @Min(value = 0)
    @ApiModelProperty(value = "所在话题的id(没有则设为0)",required = true)
    private Integer topic_id;
    @ApiModelProperty(value = "发博用户id(不用传过来,后端从token中取)",required = false)
    private Integer user_id;
}