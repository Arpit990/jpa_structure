package com.example.demo.config;

import org.jinq.jpa.JinqJPAStreamProvider;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JinqConfiguration {

    @Bean
    public JinqJPAStreamProvider jinqJPAStreamProvider(EntityManagerFactory entityManagerFactory) {
        return new JinqJPAStreamProvider(entityManagerFactory);
    }
}
