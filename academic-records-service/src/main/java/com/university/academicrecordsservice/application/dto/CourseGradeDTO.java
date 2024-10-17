package com.university.academicrecordsservice.application.dto;

import lombok.Data;

@Data
public class CourseGradeDTO {
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String grade;
    private Integer creditHours;
    private String department;
}
