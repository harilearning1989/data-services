package com.web.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@Component
public class ThreadLogger {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ThreadPoolTaskExecutor restExecutor;

    public ThreadLogger(@Qualifier("restExecutor") ThreadPoolTaskExecutor restExecutor) {
        this.restExecutor = restExecutor;
    }

    @Scheduled(fixedRate = 5000)
    public void logThreadPoolStats() {
        LOGGER.info("Thread Pool Stats:");
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        var executor = restExecutor.getThreadPoolExecutor();
        String prefix = restExecutor.getThreadNamePrefix();
        // Thread details
        StringBuilder log = new StringBuilder();
        log.append("\n========== [restExecutor Thread Pool Status] ==========\n");
        for (ThreadInfo info : threadInfos) {
            if (info.getThreadName().startsWith(prefix)) {
                log.append(String.format("→ Thread: %-20s | ID: %-5d | State: %s%n",
                        info.getThreadName(),
                        info.getThreadId(),
                        info.getThreadState()));
            }
        }

        // Pool metrics
        log.append("--------------------------------------------------------\n");
        log.append(String.format("Core Pool Size : %d%n", executor.getCorePoolSize()));
        log.append(String.format("Max Pool Size  : %d%n", executor.getMaximumPoolSize()));
        log.append(String.format("Active Threads : %d%n", executor.getActiveCount()));
        log.append(String.format("Waiting Tasks  : %d%n", executor.getQueue().size()));
        log.append(String.format("Available      : %d%n",
                executor.getMaximumPoolSize() - executor.getActiveCount()));
        log.append("========================================================");
        LOGGER.info(log.toString());
    }
}

