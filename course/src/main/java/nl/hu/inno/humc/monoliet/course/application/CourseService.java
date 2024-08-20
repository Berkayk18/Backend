package nl.hu.inno.humc.monoliet.course.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.course.application.api.interfaces.IAnnouncement;
import nl.hu.inno.humc.monoliet.course.application.api.interfaces.IEnrollment;
import nl.hu.inno.humc.monoliet.course.application.dto.*;
import nl.hu.inno.humc.monoliet.course.application.messaging.CourseMessaging;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.CourseDeleteMessage;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.NewCourseMessage;
import nl.hu.inno.humc.monoliet.course.domain.*;
import nl.hu.inno.humc.monoliet.course.domain.Module;
import nl.hu.inno.humc.monoliet.course.domain.enums.Period;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository _courseRepository;
    private final IAnnouncement announcementApi;
    private final IEnrollment enrollmentApi;

    private final CourseMessaging courseMessaging;

    public CourseService(CourseRepository courseRepository, IAnnouncement announcementApi,
                         IEnrollment enrollmentApi, CourseMessaging courseMessaging)
    {
        this._courseRepository = courseRepository;
        this.announcementApi = announcementApi;
        this.enrollmentApi = enrollmentApi;
        this.courseMessaging = courseMessaging;
    }

    public Optional<CourseDTO> getCourseByTitle(String title) {
        final Optional<Course> course = this._courseRepository.findCourseByCourseDetails_Title(title);

        if (course.isPresent()) {
            return courseToDto(course.get());
        }

        return Optional.empty();
    }

    public Optional<CourseDTO> getCourseById(Long courseId) {
        final Optional<Course> course = this._courseRepository.findById(courseId);

        if (course.isPresent()) {
            return courseToDto(course.get());
        }

        return Optional.empty();
    }

    @Transactional
    public Optional<CourseDTO> createCourse(CourseDTO courseDTO)
    {
        if (this.checkIfCourseExistByTitle(courseDTO.title())) {
            return Optional.empty();
        }

        var course = new Course(courseDTO.title(), courseDTO.period(), courseDTO.branche());
        this._courseRepository.save(course);
        this.courseMessaging.sendNewCourseMessage(new NewCourseMessage(course.getId(), course.getCourseDetails().getTitle()));
        return courseToDto(course);
    }

    @Transactional
    public Optional<CourseDTO> updateCourseById(Long courseId, CourseDTO courseDTO) {
        Optional<Course> course = this._courseRepository.findById(courseId);

        if (course.isPresent()) {
            Course existingCourse = course.get();

            // Retrieve the existing values if they are not being updated
            var title = Optional.ofNullable(courseDTO.title())
                    .orElse(existingCourse.getCourseDetails().getTitle());
            var period = Optional.ofNullable(courseDTO.period())
                    .orElse(existingCourse.getCourseDetails().getPeriod().toString());
            var branche = Optional.ofNullable(courseDTO.branche())
                    .orElse(existingCourse.getCourseDetails().getBranche().toString());

            existingCourse.updateCourse(title, period, branche);
            this._courseRepository.save(existingCourse);
            return courseToDto(existingCourse);
        }
        return Optional.empty();
    }

    public String getCourseToRemoveById(Long courseId)
    {
        var optionalCourse = this._courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();
            var announcements = this.getAnnouncementsPerCourse(course.getId());
            var enrollments = this.getEnrollmentPerCourse(course.getId());
            // In case other nodes are not responding then use data from the course
            if (announcements.isEmpty())
            {
                announcements = this.announcementsToDto(course.getAnnouncements());
            }
            if (enrollments.isEmpty())
            {
                enrollments = this.enrollmentsToDto(course.getEnrollments());
            }

            StringBuilder message = new StringBuilder("Are you sure that you want to remove this course?");

            if (!announcements.isEmpty() || !enrollments.isEmpty()) {
                message.append(" The following announcements or enrollments will be removed as well:");
                if (!announcements.isEmpty()) {
                    message.append("\nAnnouncement:");
                    for (var announcement : announcements) {
                        message.append("\n- ").append(announcement.announcer()).append(": ").append(announcement.content());
                    }
                }
                if (!enrollments.isEmpty()) {
                    message.append("\nEnrollments:");
                    for (var enrollment : enrollments) {
                        message.append("\n- ").append(enrollment.username()).append(": ").append(enrollment.type());
                    }
                }
            } else {
                message.append("This course does not includes any announcements or enrollments");
            }

            return message.toString();
        }
        return "Cursus met id '" + courseId + "' niet gevonden.";
    }

    public String removeCourseById(Long courseId)
    {
        if (this._courseRepository.existsById(courseId)) {
            this._courseRepository.deleteById(courseId);
            this.courseMessaging.sendDeleteMessage(new CourseDeleteMessage(courseId));
            return "";
        }
        return "Course with id '" + courseId + "' not found";
    }

    // Create a material for a course
    @Transactional
    public Optional<CourseDTO> createCourseMaterial(String title, String subject) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetails_Title(title);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();
            course.createNewCourseMaterial(subject);
            this._courseRepository.save(course);

            return courseToDto(course);
        }

        return Optional.empty();
    }

    public Optional<CourseMaterialDTO> getCourseMaterialBySubject(String courseTitle, String subject) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetails_Title(courseTitle);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();

            CourseMaterial courseMaterial = course.findMaterialBySubject(subject);
            if (courseMaterial == null) {
                return Optional.empty();
            }
            return courseMaterialToDto(courseMaterial);
        }

        return Optional.empty();
    }

    // Creates a new course material module
    public Optional<CourseDTO> createCourseMaterialModule(String title, String subject, ModuleDTO moduleDTO) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetails_Title(title);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();
            /* Check if the course material with the given material do exist.*/
            CourseMaterial courseMaterial = course.findMaterialBySubject(subject) ;

            if (courseMaterial == null)
            {
                return Optional.empty();
            }
            /* Add module to material.*/
            courseMaterial.createNewModule(moduleDTO.name(), moduleDTO.deadline());
            this._courseRepository.save(course);
            return courseToDto(course);
        }
        return Optional.empty();
    }

    public List<AnnouncementDTO> getAnnouncementsPerCourse(Long courseID)
    {
        return this.announcementApi.getAnnouncementPerCourse(courseID);
    }

    public List<EnrollmentDTO> getEnrollmentPerCourse(Long courseID)
    {
        return this.enrollmentApi.getEnrollmentPerCourse(courseID);
    }


    private boolean checkIfCourseExistByTitle(String title) {
        return this._courseRepository.existsByCourseDetails_Title(title);
    }

    /* This section is to set an object to dto within builders*/
    private Optional<CourseDTO> courseToDto(Course course) {
        return Optional.ofNullable(CourseDTO.builder()
                .announcementDTOs(getAnnouncementsPerCourse(course.getId()))
                .enrollmentDTOs(getEnrollmentPerCourse(course.getId()))
                .courseMaterialDTOs(this.courseMaterialsToDto(course.getCourseMaterials()))
                .title(course.getCourseDetails().getTitle())
                .period(course.getCourseDetails().getPeriod().toString())
                .branche(course.getCourseDetails().getBranche().toString())
                .build());
    }

    private List<AnnouncementDTO> announcementsToDto(List<Announcement> announcements) {
        return announcements.stream()
                .map(announcement ->
                        new AnnouncementDTO.Builder()
                                .setAnnouncer(announcement.getAnnouncer())
                                .setContent(announcement.getContent())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private List<EnrollmentDTO> enrollmentsToDto(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(enrollment ->
                        new EnrollmentDTO.Builder()
                                .setUsername(enrollment.getUsername())
                                .setType(enrollment.getUserTpe())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private List<CourseMaterialDTO> courseMaterialsToDto(List<CourseMaterial> courseMaterials) {
        return courseMaterials.stream()
                .map(courseMaterial -> {
                    List<ModuleDTO> moduleDTOList = courseMaterial.getModules().stream()
                            .map(module -> new ModuleDTO(module.getModuleContent().getName(), module.getModuleContent().getDeadline()))
                            .collect(Collectors.toList());
                    return CourseMaterialDTO.builder()
                            .moduleDTOList(moduleDTOList)
                            .subject(courseMaterial.getSubject())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Optional<CourseMaterialDTO> courseMaterialToDto(CourseMaterial courseMaterial) {
        return Optional.ofNullable(CourseMaterialDTO.builder()
                .subject(courseMaterial.getSubject())
                .moduleDTOList(this.modulesToDto(courseMaterial.getModules()))
                .build());
    }

    private List<ModuleDTO> modulesToDto(List<Module> modules) {
        return modules.stream()
                .map(module -> new ModuleDTO(
                        module.
                                getModuleContent().
                                getName(),
                        module.
                                getModuleContent()
                                .getDeadline()))
                .collect(Collectors.toList());
    }

    public Object getCountCoursesPerBranche() {
        return this._courseRepository.countAllByCourseDetail_Branche();
    }

    public Object getCountCoursesPerPeriod(String periodAsString) {
        Period period = Period.valueOf(periodAsString.toUpperCase());
        return this._courseRepository.countAllByCourseDetails_Period(period);
    }
}
