package com.example.amoy_interest.config;

import com.aliyun.oss.ClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

///**
// * @Author: Mok
// * @Date: 2020/8/4 17:05
// */
//@Configuration
//public class EsConf {
//
//    //localhost:9200 写在配置文件中就可以了
//    @Bean
//    RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration configuration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                //.withConnectTimeout(Duration.ofSeconds(5))
//                //.withSocketTimeout(Duration.ofSeconds(3))
//                //.useSsl()
//                //.withDefaultHeaders(defaultHeaders)
//                //.withBasicAuth(username, password)
//                // ... other options
//                .build();
//        RestHighLevelClient client = RestClients.create(configuration).rest();
//
//        return client;
//    }
//
//}
