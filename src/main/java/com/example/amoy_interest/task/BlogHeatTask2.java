package com.example.amoy_interest.task;

import com.example.amoy_interest.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Mok
 * @Date: 2020/8/24 20:12
 */
@Slf4j
public class BlogHeatTask2 extends QuartzJobBean {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    BlogService blogService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HeatTask2-------- {}", sdf.format(new Date()));
        blogService.updateAllBlogHeatAfterTime(null);
    }
}
