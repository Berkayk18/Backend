package nl.hu.inno.humc.courseplanning.application.dto;

import nl.hu.inno.humc.courseplanning.domain.Enrollment;

public class EnrollmentEventDTO {
    private String EventType;
    private Long courseId;
    private String username;
    private String email;
    private Enrollment.Type type;

    public EnrollmentEventDTO(Long courseId, String username, String email, Enrollment.Type type) {
        this.courseId = courseId;
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Enrollment.Type getType() {
        return type;
    }
}
