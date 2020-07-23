package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogPutDTO", description = "编辑博文信息")
public class BlogPutDTO {
    @NotNull(message = "博文id不能为空")
    @Min(value = 1,message = "博文id不能小于1")
    @ApiModelProperty(value = "编辑的博文id",required = true)
    private Integer blog_id;
    @NotBlank(message = "博文内容不能为空白")
    @Length(max = 140,message = "博文内容不能超过140字")
    @ApiModelProperty(value = "编辑的博文内容(不支持编辑图片)",required = true)
    private String text;
    @ApiModelProperty(value = "博文图片(还未实现，不需要赋值)",required = false)
    private List<String> images;
}
