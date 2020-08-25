package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.ESBlog;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: Mok
 * @Date: 2020/8/10 11:31
 */
public interface ESBlogRepository extends ElasticsearchRepository<ESBlog,Integer> {

}
