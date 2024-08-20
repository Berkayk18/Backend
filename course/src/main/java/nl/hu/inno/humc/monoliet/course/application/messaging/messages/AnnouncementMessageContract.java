package nl.hu.inno.humc.monoliet.course.application.messaging.messages;

public class AnnouncementMessageContract {

    private Long courseId;

    private String announcer;

    private String content;

    public AnnouncementMessageContract() { /*voor Jackson-JSON*/ }

    public AnnouncementMessageContract(Long courseId, String announcer, String content) {
        this.courseId = courseId;
        this.announcer = announcer;
        this.content = content;
    }

    public Long getCourseId() { return courseId; }

    public String getAnnouncer() { return announcer; }

    public String getContent() { return content; }
}
