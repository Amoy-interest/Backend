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
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogForwardDto", description = "新建博文所需内容")
public class BlogForwardDTO {
    @ApiModelProperty(value = "发博用户id(不用传过来,后端从token中取)",required = false)
    private Integer user_id;
    @NotBlank(message = "博文内容不能为空")
    @Length(max = 140, message = "博文内容不能大于140位")
    @ApiModelProperty(value = "博文文字", required = true)
    private String text;
    @Min(value = 0)
    @ApiModelProperty(value = "所在话题的id(没有则设为0)",required = true)
    private Integer topic_id;
    @NotNull(message = "转发的博文id不能为空")
    @Min(value = 0,message = "转发的博文id不能小于0")
    @ApiModelProperty(value = "转发的博文id")
    private Integer reply_blog_id;
}
