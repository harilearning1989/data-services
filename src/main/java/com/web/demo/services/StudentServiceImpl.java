package com.web.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.web.demo.download.DownloadFiles;
import com.web.demo.models.order.Student;
import com.web.demo.records.StudentRecord;
import com.web.demo.repos.order.StudentRepo;
import com.web.demo.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private StudentRepo studentRepo;

    @Autowired
    public StudentServiceImpl setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
        return this;
    }

    @Override
    public List<StudentRecord> findAll() {
        List<Student> studentList = studentRepo.findAll();
        return Optional.ofNullable(studentList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(m -> {
                    StudentRecord studentRecord =
                            new StudentRecord(m.getStudentId(), m.getStudentName(), m.getFatherName(),
                                    m.getGender(), m.getMobile(), m.getCategory(), m.getTelugu(), m.getHindi(), m.getEnglish(),
                                    m.getMaths(), m.getScience(), m.getSocial(), m.getTotal());
                    return studentRecord;
                })
                .toList();
    }

    @Override
    public List<StudentRecord> saveStudents() {
        LOGGER.info("Save All Students");
        List<StudentRecord> studentRecords = readStudentWithMarks();
        studentRecords = studentRecords.stream()
                .limit(10000)
                .toList();

        List<Student> studentList = recordToEntity(studentRecords);
        studentList.stream()
                .limit(5)
                .forEach(f -> System.out.println(f.getStudentName()));
        studentRepo.saveAll(studentList);
        return studentRecords;
    }

    private List<Student> recordToEntity(List<StudentRecord> studentRecords) {
        List<Student> studentList = studentRecords.stream()
                .map(records -> {
                    Student.StudentBuilder studentBuilder = Student.builder();
                    studentBuilder.studentId(records.studentId())
                            .studentName(records.studentName())
                            .fatherName(records.fatherName())
                            .mobile(records.mobile())
                            .category(records.category());
                    int total = records.telugu() + records.english() + records.hindi() + records.maths()
                            + records.science() + records.social();
                    if ("male".equalsIgnoreCase(records.gender())) {
                        studentBuilder.gender("Male");
                    } else {
                        studentBuilder.gender("Female");
                    }
                    studentBuilder.telugu(records.telugu())
                            .english(records.english())
                            .hindi(records.hindi())
                            .maths(records.maths())
                            .science(records.science())
                            .social(records.social())
                            .total(total);

                    Student student = studentBuilder.build();
                    return student;
                })
                .toList();
        return studentList;
    }

    @Override
    public List<StudentRecord> readStudentWithMarks() {
        String file = "json/StudentMarks.json";
        DownloadFiles.downloadFile(file);
        ObjectMapper objectMapper = new ObjectMapper();

        List<StudentRecord> studentRecords = null;
        try {
            studentRecords = objectMapper.readValue(new File(CommonUtils.fileLocation() + file),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, StudentRecord.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentRecords;
    }

    @Override
    public void readStudentCsv() throws IOException {
        LOGGER.info("read Student");
        DownloadFiles.downloadFile("csv/StudentInfo.csv");
        List<StudentRecord> listCrop = null;
        try {
            listCrop = new CsvToBeanBuilder(new FileReader(CommonUtils.fileLocation() + "csv/employee.csv"))
                    .withType(StudentRecord.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listCrop.forEach(f -> {
            System.out.println(f.studentName());
        });
    }

}
