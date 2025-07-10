package com.web.demo.controls;

import com.web.demo.services.ThreadRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("thread")
public class ThreadRestController {

    private ThreadRestService threadRestService;

    @Autowired
    public ThreadRestController setThreadRestService(ThreadRestService threadRestService) {
        this.threadRestService = threadRestService;
        return this;
    }

    @GetMapping("userPool")
    public String createUserWithThreadPoolExecutor(){
        threadRestService.createUserWithThreadPoolExecutor();
        return "hello";
    }
}
