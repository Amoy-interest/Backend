package com.example.demo.repository;

import com.example.demo.entity.BlogImage;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogImageRepository extends JpaRepository<BlogImage, Integer> {
    @Query(value = "from BlogImage where blog_id = :blog_id")
    List<BlogImage> findBlogImageByBlog_id(Integer blog_id);
}
