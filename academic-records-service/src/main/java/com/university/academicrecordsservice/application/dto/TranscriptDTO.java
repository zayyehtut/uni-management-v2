package com.university.academicrecordsservice.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class TranscriptDTO {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Double cumulativeGPA;
    private List<SemesterGradeReportDTO> semesterReports;
}
