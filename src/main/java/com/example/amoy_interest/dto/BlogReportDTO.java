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

/**
 * @Author: Mok
 * @Date: 2020/8/20 15:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogReportDto", description = "举报博文所需内容")
public class BlogReportDTO {
    @NotNull(message = "博文id不能为空")
    @Min(value = 1,message = "id不能小于1")
    @ApiModelProperty(value = "博文id",example = "1",required = true)
    private Integer blog_id;
    @NotBlank(message = "举报内容不能为空")
    @Length(max = 20, message = "举报内容不能大于20位")
    @ApiModelProperty(value = "举报内容", required = true)
    private String report_reason;
}
