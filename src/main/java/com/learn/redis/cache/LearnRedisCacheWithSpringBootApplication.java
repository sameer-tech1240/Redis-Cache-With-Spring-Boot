package com.learn.redis.cache;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class LearnRedisCacheWithSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnRedisCacheWithSpringBootApplication.class, args);
        log.info("LearnRedisCacheWithSpringBootApplication started");
    }

}
