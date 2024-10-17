package com.university.academicrecordsservice.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class SemesterGradeReportDTO {
    private String semester;
    private Integer year;
    private Double semesterGPA;
    private List<CourseGradeDTO> courseGrades;
}
