package nl.hu.inno.humc.monoliet.course.application.messaging.messages;

import java.util.UUID;

public class CourseDeleteMessage {
    private UUID uuid;

    private Long courseId;

    protected CourseDeleteMessage(){}

    public CourseDeleteMessage(Long courseId){
        this.uuid = UUID.randomUUID();
        this.courseId = courseId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getCourseId() {
        return courseId;
    }
}
