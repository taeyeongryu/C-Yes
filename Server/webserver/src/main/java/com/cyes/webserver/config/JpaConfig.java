package com.cyes.webserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// JPA 설정
@Configuration
@EnableJpaRepositories(basePackages = {
        "com.cyes.webserver.domain.member.repository"
        , "com.cyes.webserver.domain.quiz.repository"
        , "com.cyes.webserver.domain.quizproblem.repository"
        , "com.cyes.webserver.domain.quizrank.repository"})
public class JpaConfig {//위에 패키지 적어야함
}

