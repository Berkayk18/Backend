package nl.hu.inno.humc.courseplanning.domain;

import org.springframework.data.annotation.Transient;

public class CourseReference {
    private long courseId;

    @Transient
    private String title;

    protected CourseReference() {
    }

    public CourseReference(long courseId) {
        this.courseId = courseId;
    }

    public long getCourseId() {
        return courseId;
    }
}
