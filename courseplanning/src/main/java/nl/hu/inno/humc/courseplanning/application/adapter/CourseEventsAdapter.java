package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.port.CourseEventsConsumer;
import nl.hu.inno.humc.courseplanning.application.service.EnrollmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class CourseEventsAdapter implements CourseEventsConsumer {

    private final EnrollmentService enrollmentService;

    public CourseEventsAdapter(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    @RabbitListener(queues = "courses-queue")
    public void handleCourseCreated(long courseId) {
        System.out.println("Course created: " + courseId);
    }

    @Override
    @RabbitListener(queues = "courses-queue")
    public void handleCourseDeleted(long courseId) {
        enrollmentService.deleteEnrollments(courseId);
    }
}
