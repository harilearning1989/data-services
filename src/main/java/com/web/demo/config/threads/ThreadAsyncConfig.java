package com.web.demo.config.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadAsyncConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
   /* @Bean(name = "restExecutor")
    public Executor restExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("RestTemplate-");
        executor.initialize();
        return executor;
    }*/

    @Bean(name = "restExecutor")
    public ThreadPoolTaskExecutor restExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("RestTemplate-");
        /*executor.setRejectedExecutionHandler((r, exec) -> {
            LOGGER.info("ThreadAsyncConfig restExecutor Task rejected: {}", r.toString());
        });*/
        executor.initialize();
        return executor;
    }
}

