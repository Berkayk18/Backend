package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.*;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.LimitExceededException;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.DataAlreadyExistException;

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
    private List<Announcement> announcementList;


    @OneToMany(cascade = ALL)
    @JoinColumn(name = "course_id")
    private List<CourseMaterial> courseMaterialList;

    @Embedded
    private CourseDetail courseDetail;

    @Transient
    private static final int MAX_MATERIAL_LIMIT = 10;


    public Course(String title, String period, String branche)
    {
        this.courseDetail = new CourseDetail(title, period, branche);
        this.announcementList = new ArrayList<>();
        this.courseMaterialList = new ArrayList<>();
    }

    public void updateCourse(String title, String period, String branche)
    {
        this.courseDetail = new CourseDetail(title, period, branche);
    }

    public CourseMaterial getMaterialBySubject(String subject)
    {
        return this.courseMaterialList.stream()
                .filter(material -> material.getSubject().equals(subject))
                .findFirst()
                .orElse(null);
    }

    public void createNewCourseMaterial(String subject)
    {
        if (!this.checkCountAllowedMaterialsIsAchieved())
        {
            throw new LimitExceededException("Materials");
        }
        else if(checkIfMaterialWithGivenSubjectAlreadyExist(subject))
        {
            throw new DataAlreadyExistException("Material");
        }
        this.courseMaterialList.add(new CourseMaterial(subject));
    }

    private boolean checkCountAllowedMaterialsIsAchieved()
    {
        return this.courseMaterialList.size() < MAX_MATERIAL_LIMIT;
    }

    public boolean checkIfMaterialWithGivenSubjectAlreadyExist(String subject)
    {
        return this.courseMaterialList.stream()
                .anyMatch(material -> material.getSubject().equals(subject));
    }

    protected Course() {}

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    protected void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }

    public List<CourseMaterial> getCourseMaterialList() {
        return courseMaterialList;
    }

    protected void setCourseMaterialList(List<CourseMaterial> courseMaterialList) {
        this.courseMaterialList = courseMaterialList;
    }

    public List<Announcement> getAnnouncementList()
    {
        return this.announcementList;
    }
}
