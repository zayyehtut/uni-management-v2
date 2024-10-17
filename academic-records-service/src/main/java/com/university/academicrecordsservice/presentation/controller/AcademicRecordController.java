package com.university.academicrecordsservice.presentation.controller;

import com.university.academicrecordsservice.application.service.AcademicRecordService;
import com.university.academicrecordsservice.domain.model.AcademicRecord;
import com.university.academicrecordsservice.domain.model.Grade;
import com.university.academicrecordsservice.application.dto.TranscriptDTO;
import com.university.academicrecordsservice.application.dto.SemesterGradeReportDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academic-records")
@RequiredArgsConstructor
public class AcademicRecordController {

    private final AcademicRecordService academicRecordService;

    @GetMapping("/{studentId}")
    public ResponseEntity<AcademicRecord> getAcademicRecord(@PathVariable Long studentId) {
        return ResponseEntity.ok(academicRecordService.getAcademicRecordByStudentId(studentId));
    }

    @PostMapping("/{studentId}/grades")
    public ResponseEntity<AcademicRecord> addGrade(@PathVariable Long studentId, @Valid @RequestBody Grade grade) {
        return new ResponseEntity<>(academicRecordService.addGrade(studentId, grade), HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}/grades/{gradeId}")
    public ResponseEntity<AcademicRecord> updateGrade(
            @PathVariable Long studentId,
            @PathVariable Long gradeId,
            @Valid @RequestBody Grade updatedGrade) {
        return ResponseEntity.ok(academicRecordService.updateGrade(studentId, gradeId, updatedGrade));
    }

    @DeleteMapping("/{studentId}/grades/{gradeId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long studentId, @PathVariable Long gradeId) {
        academicRecordService.deleteGrade(studentId, gradeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}/gpa")
    public ResponseEntity<Double> calculateGPA(@PathVariable Long studentId) {
        return ResponseEntity.ok(academicRecordService.calculateGPA(studentId));
    }

    @GetMapping("/{studentId}/transcript")
    public ResponseEntity<TranscriptDTO> generateTranscript(@PathVariable Long studentId) {
        return ResponseEntity.ok(academicRecordService.generateTranscript(studentId));
    }

    @GetMapping("/{studentId}/semester-report")
    public ResponseEntity<SemesterGradeReportDTO> generateSemesterGradeReport(
            @PathVariable Long studentId,
            @RequestParam String semester,
            @RequestParam Integer year) {
        return ResponseEntity.ok(academicRecordService.generateSemesterGradeReport(studentId, semester, year));
    }
}
