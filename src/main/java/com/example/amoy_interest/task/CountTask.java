package com.example.amoy_interest.task;
import com.example.amoy_interest.service.CountService;
import com.example.amoy_interest.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @Author: Mok
 * @Date: 2020/7/29 18:00
 */
@Slf4j
public class CountTask extends QuartzJobBean {

    @Autowired
    VoteService voteService;
    @Autowired
    CountService countService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("VoteTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        voteService.transVoteFromRedis2DB();
        voteService.transVoteCountFromRedis2DB();

        log.info("CountTask-------- {}", sdf.format(new Date()));//先放在一起

        countService.transBlogCountDataFromRedis2DB();
        countService.transBlogReportDataFromRedis2DB();
        countService.transUserCountDataFromRedis2DB();
        countService.transUserReporterDataFromRedis2DB();
    }
}
