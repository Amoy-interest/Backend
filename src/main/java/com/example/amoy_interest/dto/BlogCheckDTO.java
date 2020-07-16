package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "BlogCheckDTO", description = "博文审核信息")
public class BlogCheckDTO {
    Integer blog_id;
    Integer check_status;
}
