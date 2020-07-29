package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.BlogImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogContentDto", description = "博文内容")
public class BlogContentDTO {
    @NotBlank(message = "博文内容不能为空")
    @Length(max = 140, message = "博文内容不能大于140位")
    @ApiModelProperty(value = "博文文字", required = true)
    private String text;
    @ApiModelProperty(value = "博文图片", required = false)
    private List<String> images;

    public BlogContentDTO(Blog blog) {
        this.images = null;
        if (blog.getBlogImages() != null) {
            this.images = new ArrayList<>();
            for (BlogImage blogImage : blog.getBlogImages()) {
                images.add(blogImage.getBlog_image());
            }
        }
        this.text = blog.getBlog_text();
    }
}


