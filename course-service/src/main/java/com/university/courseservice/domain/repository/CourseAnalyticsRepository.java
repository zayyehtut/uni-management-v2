package com.university.courseservice.domain.repository;

import com.university.courseservice.domain.model.CourseAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAnalyticsRepository extends JpaRepository<CourseAnalytics, Long> {
    Optional<CourseAnalytics> findByCourseId(Long courseId);
}