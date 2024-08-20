package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.*;
import nl.hu.inno.humc.monoliet.course.domain.embeddables.AnnouncementTotalPerCourse;
import nl.hu.inno.humc.monoliet.course.domain.embeddables.CourseDetails;
import nl.hu.inno.humc.monoliet.course.domain.embeddables.EnrollmentsTotalPerCourse;
import nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions;
import nl.hu.inno.humc.monoliet.course.domain.enums.InstancesCount;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.DataAlreadyExistException;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.LimitExceededException;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "course_id")
    private List<Announcement> announcements;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "course_id")
    private List<Enrollment> enrollments;


    @OneToMany(cascade = ALL)
    @JoinColumn(name = "course_id")
    private List<CourseMaterial> courseMaterials;

    @Embedded
    private CourseDetails courseDetails;

    @Embedded
    private EnrollmentsTotalPerCourse totalEnrollmentsPerCourse;

    @Embedded
    private AnnouncementTotalPerCourse totalAnnouncementPerCourse;

    @Transient
    private static final int MAX_MATERIAL_LIMIT = 10;


    public Course(String title, String period, String branche)
    {
        this.courseDetails = new CourseDetails(title, period, branche);
        this.announcements = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.courseMaterials = new ArrayList<>();
        this.totalAnnouncementPerCourse = new AnnouncementTotalPerCourse(0);
        this.totalEnrollmentsPerCourse = new EnrollmentsTotalPerCourse(0);
    }

    protected Course() {}

    public void updateCourse(String title, String period, String branche)
    {
        this.courseDetails = new CourseDetails(title, period, branche);
    }

    public CourseMaterial findMaterialBySubject(String subject)
    {
        return this.courseMaterials.stream()
                .filter(material -> material.getSubject().equals(subject))
                .findFirst()
                .orElse(null);
    }

    public void createNewCourseMaterial(String subject)
    {
        if (!this.hasReachedMaterialLimit())
        {
            throw new LimitExceededException("Materials");
        }
        else if(doesMaterialWithSubjectExist(subject))
        {
            throw new DataAlreadyExistException("Material");
        }
        this.courseMaterials.add(new CourseMaterial(subject));
    }

    private boolean hasReachedMaterialLimit()
    {
        return this.courseMaterials.size() < MAX_MATERIAL_LIMIT;
    }

    public boolean doesMaterialWithSubjectExist(String subject)
    {
        return this.courseMaterials.stream()
                .anyMatch(material -> material.getSubject().equals(subject));
    }

    public void addNewAnnouncement(String announcer, String content)
    {
        this.announcements.add(new Announcement(announcer, content));
    }

    public void removeAnnouncement(String announcer, String content) {
        announcements.removeIf(announcement ->
                announcement.getAnnouncer().equals(announcer) && announcement.getContent().equals(content));
    }

    public void addNewEnrollment(String username, String userTpe)
    {
        this.enrollments.add(new Enrollment(username, userTpe));
    }

    public void removeEnrollment(String username, String userTpe) {
        enrollments.removeIf(enrollment ->
                enrollment.getUsername().equals(username) && enrollment.getUserTpe().equals(userTpe));
    }

    public void updateInstanceCount(int currentCount, InstancesCount classToChange, InstanceActions action)
    {
        switch (classToChange) {
            case ANNOUNCEMENT -> {
                this.totalAnnouncementPerCourse = new AnnouncementTotalPerCourse(currentCount);
                this.totalAnnouncementPerCourse.updateTotalAnnouncements(action);
            }
            case ENROLLMENTS -> {
                this.totalEnrollmentsPerCourse = new EnrollmentsTotalPerCourse(currentCount);
                this.totalEnrollmentsPerCourse.updateTotalEnrollments(action);
            }
            default ->
                    throw new IllegalArgumentException("It's not allowed to modify the count of the provided instance");
        }
    }

    public Long getId() {
        return id;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public List<CourseMaterial> getCourseMaterials() {
        return courseMaterials;
    }

    public AnnouncementTotalPerCourse getTotalAnnouncementPerCourse() { return this.totalAnnouncementPerCourse; }

    public EnrollmentsTotalPerCourse getTotalEnrollmentsPerCourse() { return this.totalEnrollmentsPerCourse;}

    public List<Announcement> getAnnouncements() { return this.announcements;}

    public List<Enrollment> getEnrollments()
    {
        return this.enrollments;
    }
}
