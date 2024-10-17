package com.university.academicrecordsservice.application.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String courseCode;
    private String name;
    private String description;
    private int creditHours;
    private String department;
}
