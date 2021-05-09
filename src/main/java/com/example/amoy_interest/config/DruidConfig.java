package com.example.amoy_interest.config;

/**
 * @Author: Mok
 * @Date: 2020/7/28 16:41
 */
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * mysql监控
 */
@Configuration
public class DruidConfig {
    /**
     * 监控服务器
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean =new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //IP白名单
//        servletRegistrationBean.addInitParameter("allow","*");
//        servletRegistrationBean.addInitParameter("allow","192.168.3.107,192.168.3.111,192.168.3.113");
        //IP黑名单
//        servletRegistrationBean.addInitParameter("deny","192.168.3.10");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername","druid");
        servletRegistrationBean.addInitParameter("loginPassword","1234");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","true");
        return servletRegistrationBean;
    }

    /**
     * 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean =new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加过滤格式 过滤掉这些访问
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * Druid的控台是监控不了SQL的解决方案
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
