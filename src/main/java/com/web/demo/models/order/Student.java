package com.web.demo.models.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STUDENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private long studentId;
    @Column(name = "STUDENT_NAME")
    private String studentName;
    @Column(name = "FATHER_NAME")
    private String fatherName;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "TELUGU")
    private int telugu;
    @Column(name = "HINDI")
    private int hindi;
    @Column(name = "ENGLISH")
    private int english;
    @Column(name = "MATHS")
    private int maths;
    @Column(name = "SCIENCE")
    private int science;
    @Column(name = "SOCIAL")
    private int social;
    @Column(name = "TOTAL")
    private int total;
}
