package com.web.demo.records;

public record StudentRecord(
        long studentId,
        String studentName,
        String fatherName,
        String gender,
        String mobile,
        String category,
        int telugu,
        int hindi,
        int english,
        int maths,
        int science,
        int social,
        int total
) {

}
