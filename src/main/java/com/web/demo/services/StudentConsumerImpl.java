package com.web.demo.services;

import com.web.demo.dtos.StudentRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentConsumerImpl {

    @KafkaListener(topics = "employee_data", groupId = "employee_group")
    public void consume(StudentRecord student) {
        System.out.println("Consumed student: " + student);
    }
}

