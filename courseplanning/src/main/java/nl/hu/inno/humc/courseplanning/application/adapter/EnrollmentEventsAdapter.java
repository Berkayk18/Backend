package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.dto.EnrollmentEventDTO;
import nl.hu.inno.humc.courseplanning.application.port.EnrollmentEventsPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentEventsAdapter implements EnrollmentEventsPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EnrollmentEventsAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishCourseEnrollmentEvent(EnrollmentEventDTO enrollmentEventDTO) {
        enrollmentEventDTO.setEventType("ENROLLMENT_CREATED");
        rabbitTemplate.convertAndSend("enrollments", "", enrollmentEventDTO);

    }

    @Override
    public void publishEnrollmentDeletedEvent(EnrollmentEventDTO enrollmentEventDTO) {
        enrollmentEventDTO.setEventType("ENROLLMENT_DELETED");
        rabbitTemplate.convertAndSend("enrollments", "", enrollmentEventDTO);
    }
}
