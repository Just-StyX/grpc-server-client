package com.jsl.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Value("${grpc.datasource.url}")
    private String url;
    @Value("${grpc.datasource.password}")
    private String password;
    @Value("${grpc.datasource.username}")
    private String username;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username).build();
    }
}
