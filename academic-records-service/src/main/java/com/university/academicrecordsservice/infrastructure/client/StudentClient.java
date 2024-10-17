package com.university.academicrecordsservice.infrastructure.client;

import com.university.academicrecordsservice.application.dto.StudentDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "${student-service.url}")
public interface StudentClient {

    @GetMapping("/api/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);
}