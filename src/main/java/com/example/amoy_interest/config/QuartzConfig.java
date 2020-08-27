package com.example.amoy_interest.config;

import com.example.amoy_interest.task.CountTask;
import com.example.amoy_interest.task.BlogHeatTask;
import com.example.amoy_interest.task.BlogHeatTask2;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Mok
 * @Date: 2020/7/29 17:58
 */
@Configuration
public class QuartzConfig {

    private static final String COUNT_TASK_IDENTITY = "CountTaskQuartz";
    private static final String BLOG_HEAT_TASK_IDENTITY = "BlogHeatQuartz";
    private static final String BLOG_HEAT_TASK_2_IDENTITY = "BlogHeat2Quartz";

//    @Bean
//    public JobDetail jobDetail1() {
//        return JobBuilder.newJob(CountTask.class).withIdentity(COUNT_TASK_IDENTITY).storeDurably().build();
//    }
//
//    @Bean
//    public JobDetail jobDetail2() {
//        return JobBuilder.newJob(BlogHeatTask.class).withIdentity(BLOG_HEAT_TASK_IDENTITY).storeDurably().build();
//    }
//
//    @Bean
//    public JobDetail jobDetail3() {
//        return JobBuilder.newJob(BlogHeatTask2.class).withIdentity(BLOG_HEAT_TASK_2_IDENTITY).storeDurably().build();
//    }
//
//    @Bean
//    public Trigger quartzTrigger1() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
////                .withIntervalInSeconds(10)  //设置时间周期单位秒
//                .withIntervalInHours(2)  //两个小时执行一次
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(jobDetail1())
//                .withIdentity(COUNT_TASK_IDENTITY)
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
//
//    @Bean
//    public Trigger quartzTrigger2() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
////                .withIntervalInSeconds(10)  //设置时间周期单位秒
//                .withIntervalInHours(1)  //一个小时执行一次
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(jobDetail2())
//                .withIdentity(BLOG_HEAT_TASK_IDENTITY)
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
//
//    @Bean
//    public Trigger quartzTrigger3() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
////                .withIntervalInSeconds(10)  //设置时间周期单位秒
//                .withIntervalInHours(24)  //每天执行一次
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(jobDetail3())
//                .withIdentity(BLOG_HEAT_TASK_2_IDENTITY)
//                .withSchedule(scheduleBuilder)
//                .build();
//    }


}
