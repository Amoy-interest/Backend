package com.example.amoy_interest.service;

/**
 * @Author: Mok
 * @Date: 2020/8/20 13:15
 */
public interface CountService {
    void transBlogCountDataFromRedis2DB();

    void transBlogReportDataFromRedis2DB();

    void transUserCountDataFromRedis2DB();

    void transUserReporterDataFromRedis2DB();
}
