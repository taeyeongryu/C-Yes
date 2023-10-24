package com.cyes.webserver.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "")
public class MongoConfig {
    //위에 패키지 적어야함
}