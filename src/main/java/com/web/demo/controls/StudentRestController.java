package com.web.demo.controls;

import com.web.demo.records.StudentRecord;
import com.web.demo.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("data")
public class StudentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRestController.class);

    private StudentService studentService;

    @Autowired
    public StudentRestController setStudentService(StudentService studentService) {
        this.studentService = studentService;
        return this;
    }

    @GetMapping("list")
    public List<StudentRecord> findAll() {
        return studentService.findAll();
    }

    @GetMapping("saveStudents")
    public List<StudentRecord> saveStudents() {
        return studentService.saveStudents();
    }

    @GetMapping("readStudentJson")
    public List<StudentRecord> readStudentJson() {
        return studentService.readStudentWithMarks();
    }
    @GetMapping("readStudentCsv")
    public String readStudentCsv() {
        LOGGER.info("read Student");
        try {
            studentService.readStudentCsv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Read Success";
    }
}
