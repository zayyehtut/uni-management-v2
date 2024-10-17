package com.university.enrollmentservice.domain.repository;

import com.university.enrollmentservice.domain.model.AvailableCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableCourseRepository extends JpaRepository<AvailableCourse, Long> {
}