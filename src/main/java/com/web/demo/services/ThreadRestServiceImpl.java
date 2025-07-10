package com.web.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ThreadRestServiceImpl implements ThreadRestService {

    @Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;

    @Override
    @Async("threadPoolExecutor")
    public void createUserWithThreadPoolExecutor() {
        System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
        System.out.println("User created with thread pool executor");
    }

    public <T> Future<T> execute(Callable<T> callable) {
        return executorService.submit(callable);
    }
}
