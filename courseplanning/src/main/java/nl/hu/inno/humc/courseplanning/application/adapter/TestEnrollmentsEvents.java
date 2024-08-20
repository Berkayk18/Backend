package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.dto.EnrollmentEventDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestEnrollmentsEvents {
    @RabbitListener(queues = "test-enrollments-queue")
    public void handleEnrollmentEvent(EnrollmentEventDTO enrollmentEventDTO) {
        if (enrollmentEventDTO.getEventType().equals("ENROLLMENT_CREATED")) {
            System.out.println(enrollmentEventDTO.getUsername() + " is enrolled in course " + enrollmentEventDTO.getCourseId() + " as a "
                    + enrollmentEventDTO.getType() + " with email " + enrollmentEventDTO.getEmail());
        } else if (enrollmentEventDTO.getEventType().equals("ENROLLMENT_DELETED")) {
            System.out.println(enrollmentEventDTO.getUsername() + " is unenrolled from course " + enrollmentEventDTO.getCourseId());
        } else {
            System.out.println("Unknown event type: " + enrollmentEventDTO.getEventType());
        }
    }
}
