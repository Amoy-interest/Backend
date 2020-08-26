package com.example.amoy_interest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/24 19:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogHeatParam {
    private Integer blog_id;
    private Integer forward_count;
    private Integer vote_count;
    private Integer comment_count;
    private Date create_time;
}
