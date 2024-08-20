package nl.hu.inno.humc.courseplanning.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "enrollments")
public class Enrollment {
    @Id
    private UUID id;

    @DBRef
    private User user;

    private CourseReference courseReference;

    public enum Type {
        STUDENT,
        TEACHER,
        DESIGNER,
    }

    private Type type;

    protected Enrollment() {
    }

    public Enrollment(User user, CourseReference courseReference, Type type) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.courseReference = courseReference;
        this.type = type;
    }

    public User getUser() {
            return user;
    }

    public Type getType() {
        return type;
    }

    public CourseReference getCourseReference() {
        return courseReference;
    }
}