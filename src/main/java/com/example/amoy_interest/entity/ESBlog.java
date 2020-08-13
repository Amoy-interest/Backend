package com.example.amoy_interest.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/10 11:19
 */
@Document(indexName = "Blog")
@Getter
@Setter
public class ESBlog implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Integer id;
    private Integer user_id;
    private Integer topic_id;
    private Integer blog_type;
    private Date blog_time;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String blog_text;
    private boolean is_deleted;
    private Integer check_status;
    private Integer reply_blog_id;
}
