package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Mok
 * @Date: 2020/7/29 15:20
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogVoteCountDTO {
    private Integer blog_id;
    private Integer vote_count;
}
