package com.example.amoy_interest.task;

import com.example.amoy_interest.service.TopicService;
import lombok.SneakyThrows;
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
 * @Date: 2020/9/6 11:42
 */
@Slf4j
public class TopicHeatTask extends QuartzJobBean {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TopicService topicService;
    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HeatTask-------- {}", sdf.format(new Date()));
//        topicService.updateAllTopicHeat();
    }
}
