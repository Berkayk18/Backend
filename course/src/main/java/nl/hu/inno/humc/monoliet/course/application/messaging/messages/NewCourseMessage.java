package nl.hu.inno.humc.monoliet.course.application.messaging.messages;

import java.util.UUID;

public class NewCourseMessage {
    private UUID uuid;

    private Long courseId;

    private String courseTitle;

    protected NewCourseMessage(){}

    public NewCourseMessage(Long courseId, String courseTitle){
        this.courseId = courseId;
        this.uuid = UUID.randomUUID();
        this.courseTitle = courseTitle;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
}
