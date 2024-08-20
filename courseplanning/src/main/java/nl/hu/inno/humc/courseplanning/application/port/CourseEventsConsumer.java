package nl.hu.inno.humc.courseplanning.application.port;

public interface CourseEventsConsumer {
    void handleCourseCreated(long courseId);

    void handleCourseDeleted(long courseId);
}
