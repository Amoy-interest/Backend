package com.example.amoy_interest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "CommentPostDTO", description = "新建评论信息")
public class CommentPostDTO {
    @NotNull(message = "博文id不能为空")
    @Min(value = 1,message = "博文id不能小于1")
    @ApiModelProperty(value = "评论所在的blog的id",required = true)
    private Integer blog_id;
    @NotNull(message = "根评论id不能为空")
    @Min(value = 0, message = "根评论id不能小于0")
    @ApiModelProperty(value = "多级评论的根评论id(如果本身是一级评论，则为0)",required =  true)
    private Integer root_comment_id;
    @NotNull(message = "被评论者id不能为空")
    @Min(value = 0, message = "被评论者id不能小于0")
    @ApiModelProperty(value = "被评论者的id(多级评论的时候才有,一级评论为0)",required = true)
    private Integer reply_user_id;
    @NotBlank(message = "评论内容不能为空白")
    @Length(max = 140,message = "评论内容不能大于140字")
    @ApiModelProperty(value = "评论内容",required = true)
    private String text;
}
