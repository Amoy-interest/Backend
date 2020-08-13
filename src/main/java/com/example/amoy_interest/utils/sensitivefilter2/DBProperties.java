package com.example.amoy_interest.utils.sensitivefilter2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Author: Mok
 * @Date: 2020/8/13 10:02
 */
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class DBProperties {
    private String url;
    private String username;
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String className;
}
