package com.university.enrollmentservice.infrastructure.messaging;

import com.university.enrollmentservice.application.dto.CourseDTO;
import com.university.enrollmentservice.application.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class EnrollmentConsumer {

    private final EnrollmentService enrollmentService;

    @Bean
    public Consumer<Message<CourseDTO>> courseCreatedConsumer() {
        return message -> {
            CourseDTO courseDTO = message.getPayload();
            enrollmentService.addNewCourseToAvailableCourses(courseDTO);
        };
    }
}