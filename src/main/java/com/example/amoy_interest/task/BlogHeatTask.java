package com.example.amoy_interest.task;

import com.example.amoy_interest.config.QuartzConfig;
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
 * @Date: 2020/8/24 19:58
 */
@Slf4j
public class BlogHeatTask extends QuartzJobBean {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    BlogService blogService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HeatTask-------- {}", sdf.format(new Date()));
        Calendar before3Day = Calendar.getInstance();
        before3Day.setTime(new Date());
        before3Day.add(Calendar.DAY_OF_YEAR,-3);//前三天的blog
        blogService.updateAllBlogHeatAfterTime(before3Day.getTime());
    }
}
