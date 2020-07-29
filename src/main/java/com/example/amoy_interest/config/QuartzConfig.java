package com.example.amoy_interest.config;

import com.example.amoy_interest.task.VoteTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Author: Mok
 * @Date: 2020/7/29 17:58
 */
@Configuration
public class QuartzConfig {

    private static final String VOTE_TASK_IDENTITY = "VoteTaskQuartz";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(VoteTask.class).withIdentity(VOTE_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10)  //设置时间周期单位秒
                .withIntervalInHours(2)  //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(VOTE_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
