package com.example.amoy_interest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TopicHeatParam {
    /**
     * 该topic的id
     */
    private Integer topic_id;
    /**
     * 该topic所有博文的点赞数
     */
    private Integer vote_count;

    /**
     * 该topic所有博文的转发数
     */
    private Integer forward_count;

    /**
     * 该topic所有博文的评论数
     */
    private Integer comment_count;
    /**
     * 话题创建时间
     */
    private Date topic_time;



}
