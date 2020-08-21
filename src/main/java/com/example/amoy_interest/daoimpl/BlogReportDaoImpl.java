package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.BlogReportDao;
import com.example.amoy_interest.entity.BlogReport;
import com.example.amoy_interest.repository.BlogReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:51
 */
@Repository
public class BlogReportDaoImpl implements BlogReportDao {
    @Autowired
    private BlogReportRepository blogReportRepository;

    @Override
    public void save(BlogReport blogReport) {
        blogReportRepository.save(blogReport);
    }

    @Override
    public void saveAll(List<BlogReport> list) {
        blogReportRepository.saveAll(list);
    }

    @Override
    public BlogReport getByBlogIdAndUserId(Integer blog_id, Integer user_id) {
        return blogReportRepository.getByBlog_idAndUser_id(blog_id, user_id);
    }
}
