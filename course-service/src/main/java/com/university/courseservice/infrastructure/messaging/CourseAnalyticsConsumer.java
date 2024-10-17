package com.university.courseservice.infrastructure.messaging;

import com.university.courseservice.application.service.CourseService;
import com.university.courseservice.infrastructure.event.EnrollmentCreatedEvent;
import com.university.courseservice.infrastructure.event.GradeSubmittedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class CourseAnalyticsConsumer {
    private final CourseService courseService;

    @Bean
    public Consumer<EnrollmentCreatedEvent> enrollmentCreatedConsumer() {
        return event -> courseService.updateEnrollmentCount(event.getCourseId());
    }

    @Bean
    public Consumer<GradeSubmittedEvent> gradeSubmittedConsumer() {
        return event -> courseService.updateAverageGrade(event.getCourseId(), event.getGrade());
    }
}