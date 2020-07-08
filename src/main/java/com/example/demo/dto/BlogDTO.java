package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogDto", description = "博文信息")
public class BlogDTO {
    private Integer blog_type;
    private Date blog_time;
    private BlogContentDTO blogContent;
    private BlogChild blogChild;
    private BlogCountDTO blogCount;

}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogChild", description = "转发的博文信息")
class BlogChild {
    private String text;
    private List<String> images;
}
