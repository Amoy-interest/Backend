package com.example.amoy_interest.dto;

import com.example.amoy_interest.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "UserReportDTO", description = "被举报用户信息")
public class UserReportDTO {
    @ApiModelProperty(value = "被举报用户的id",required = true)
    private Integer user_id;
    @ApiModelProperty(value = "被举报用户的昵称",required = true)
    private String nickname;
    @ApiModelProperty(value = "被举报用户的被举报数",required = true)
    private Integer report_count;
    //是否需要举报原因？
    public UserReportDTO(User user,Integer report_count) {
        this.user_id = user.getUser_id();
        this.nickname = user.getNickname();
        this.report_count = report_count;
    }
}
