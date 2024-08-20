package nl.hu.inno.humc.monoliet.course.application.messaging.messages;

public class UnexistingCourseMessage {
    private Long courseId;

    protected UnexistingCourseMessage(){}

    public UnexistingCourseMessage(Long courseId){
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }
}
