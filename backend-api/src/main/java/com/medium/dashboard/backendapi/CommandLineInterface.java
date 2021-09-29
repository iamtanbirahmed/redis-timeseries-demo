package com.medium.dashboard.backendapi;

import com.medium.dashboard.backendapi.service.RedisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInterface implements CommandLineRunner {

    private RedisService redisService;

    public CommandLineInterface(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Testing redis....");
        redisService.testRedis();
    }
}
