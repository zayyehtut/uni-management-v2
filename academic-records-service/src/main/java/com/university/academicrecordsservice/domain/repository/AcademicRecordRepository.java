package com.university.academicrecordsservice.domain.repository;

import com.university.academicrecordsservice.domain.model.AcademicRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, Long> {
    Optional<AcademicRecord> findByStudentId(Long studentId);
}