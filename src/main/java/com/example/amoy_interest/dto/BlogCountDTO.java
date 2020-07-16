package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.BlogCount;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "BlogCountDto", description = "博文计数信息")
public class BlogCountDTO {
    private Integer forward_count;
    private Integer comment_count;
    private Integer vote_count;
    private Integer report_count;

    public BlogCountDTO(BlogCount blogCount) {
        this.setComment_count(blogCount.getComment_count());
        this.setForward_count(blogCount.getForward_count());
        this.setVote_count(blogCount.getVote_count());
        this.setReport_count(blogCount.getReport_count());
    }
}
