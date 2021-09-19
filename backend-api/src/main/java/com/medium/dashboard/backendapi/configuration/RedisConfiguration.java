package com.medium.dashboard.backendapi.configuration;

import com.redislabs.redistimeseries.RedisTimeSeries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisTimeSeries redisTimeSeries() {
        return new RedisTimeSeries("localhost", 6379);
    }
}
