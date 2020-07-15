package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogChild", description = "转发的博文信息")
public class BlogChildDTO {
    private int user_id;
    private String nickname;
    private String text;
    private List<String> images;
}
