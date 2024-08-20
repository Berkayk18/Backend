package nl.hu.inno.humc.courseplanning.application.port;

import nl.hu.inno.humc.courseplanning.application.dto.EnrollmentEventDTO;

public interface EnrollmentEventsPublisher {
    void publishCourseEnrollmentEvent(EnrollmentEventDTO enrollmentEventDTO);

    void publishEnrollmentDeletedEvent(EnrollmentEventDTO enrollmentEventDTO);
}
