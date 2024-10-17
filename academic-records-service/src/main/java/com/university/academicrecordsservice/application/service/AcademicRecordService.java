package com.university.academicrecordsservice.application.service;

import com.university.academicrecordsservice.domain.model.AcademicRecord;
import com.university.academicrecordsservice.domain.model.Grade;
import com.university.academicrecordsservice.application.dto.TranscriptDTO;
import com.university.academicrecordsservice.application.dto.SemesterGradeReportDTO;

public interface AcademicRecordService {
    AcademicRecord getAcademicRecordByStudentId(Long studentId);
    AcademicRecord addGrade(Long studentId, Grade grade);
    AcademicRecord updateGrade(Long studentId, Long gradeId, Grade updatedGrade);
    void deleteGrade(Long studentId, Long gradeId);
    double calculateGPA(Long studentId);
    void initializeAcademicRecord(Long studentId);

    // New methods for transcript and semester-wise grade report
    TranscriptDTO generateTranscript(Long studentId);
    SemesterGradeReportDTO generateSemesterGradeReport(Long studentId, String semester, Integer year);
}
