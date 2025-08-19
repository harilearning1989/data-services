package com.web.demo.services;

import com.web.demo.dtos.StudentRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentProducerImppl {

    private static final String TOPIC = "employee_data";

    private final KafkaTemplate<String, StudentRecord> kafkaTemplate;

    public StudentProducerImppl(KafkaTemplate<String, StudentRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStudent(StudentRecord student) {
        kafkaTemplate.send(TOPIC, student);
    }
}

