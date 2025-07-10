package com.web.demo.services;

import com.web.demo.records.StudentRecord;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    void readStudentCsv() throws IOException;

    List<StudentRecord> readStudentWithMarks();

    List<StudentRecord> saveStudents();

    List<StudentRecord> findAll();
}
