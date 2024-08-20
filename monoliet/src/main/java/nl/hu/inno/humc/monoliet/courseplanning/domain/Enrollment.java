package nl.hu.inno.humc.monoliet.courseplanning.domain;

import jakarta.persistence.*;
import nl.hu.inno.humc.monoliet.course.domain.Course;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public enum Type {
        STUDENT,
        TEACHER,
        DESIGNER,
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    protected Enrollment() {
    }

    public Enrollment(User user, Course course, Type type) {
        this.user = user;
        this.course = course;
        this.type = type;
    }

    public User getUser() {
            return user;
    }

    public Type getType() {
        return type;
    }
}