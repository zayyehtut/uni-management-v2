package com.university.academicrecordsservice.infrastructure.messaging;

import com.university.academicrecordsservice.application.service.AcademicRecordService;
import com.university.academicrecordsservice.application.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class AcademicRecordsConsumer {

    private final AcademicRecordService academicRecordService;

    @Bean
    public Consumer<StudentDTO> studentCreatedConsumer() {
        return studentDTO -> academicRecordService.initializeAcademicRecord(studentDTO.getId());
    }
}