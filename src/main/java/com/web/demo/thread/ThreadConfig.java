package com.web.demo.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/*@Configuration
@EnableAsync*/
public class ThreadConfig {

    @Bean(name = "threadPoolExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("threadPoolExecutor-");
        executor.setCorePoolSize(100);
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor().CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
