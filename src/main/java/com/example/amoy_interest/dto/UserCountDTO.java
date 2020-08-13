package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.BlogCount;
import com.example.amoy_interest.entity.UserCount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Mok
 * @Date: 2020/7/30 9:39
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "UserCountDto", description = "用户计数信息")
public class UserCountDTO {
    @ApiModelProperty(value = "关注数")
    private Integer follow_count;
    @ApiModelProperty(value = "粉丝数")
    private Integer fan_count;
    @ApiModelProperty(value = "博文数")
    private Integer blog_count;
    public UserCountDTO(UserCount userCount) {
        this.blog_count = userCount.getBlog_count();
        this.fan_count = userCount.getFan_count();
        this.follow_count = userCount.getFollow_count();
    }
}
