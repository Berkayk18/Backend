package nl.hu.inno.humc.monoliet.course.application.messaging.messages;

public class EnrollmentMessageContract {

    private Long courseId;
    private String username;
    private String userType;

    public EnrollmentMessageContract() { /*voor Jackson-JSON*/ }

    public EnrollmentMessageContract(Long courseId, String username, String userType) {
        this.courseId = courseId;
        this.username = username;
        this.userType = userType;
    }

    public Long getCourseId() { return courseId; }

    public String getUsername() { return username; }

    public String getUserType() { return userType; }
}
