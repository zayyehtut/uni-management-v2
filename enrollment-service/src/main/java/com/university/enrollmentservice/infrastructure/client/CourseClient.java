package com.university.enrollmentservice.infrastructure.client;
import com.university.enrollmentservice.application.dto.CourseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "${course-service.url}")
public interface CourseClient {

    @GetMapping("/api/courses/{id}")
    CourseDTO getCourseById(@PathVariable("id") Long id);
}