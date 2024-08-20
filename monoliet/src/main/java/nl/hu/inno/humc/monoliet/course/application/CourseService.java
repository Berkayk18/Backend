package nl.hu.inno.humc.monoliet.course.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.course.application.dto.AnnouncementDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseMaterialDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.ModuleDTO;
import nl.hu.inno.humc.monoliet.course.domain.CourseMaterial;
import nl.hu.inno.humc.monoliet.course.domain.Module;
import nl.hu.inno.humc.monoliet.course.domain.enums.Period;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseDTO;
import nl.hu.inno.humc.monoliet.course.domain.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository _courseRepository;
    private final AnnouncementApi announcementApi;

    public CourseService(CourseRepository courseRepository, AnnouncementApi announcementApi)
    {
        this._courseRepository = courseRepository;
        this.announcementApi = announcementApi;
    }

    public Optional<CourseDTO> getCourseByTitle(String title) {
        final Optional<Course> course = this._courseRepository.findCourseByCourseDetail_Title(title);

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
        return courseToDto(course);
    }

    @Transactional
    public Optional<CourseDTO> updateCourseById(Long courseId, CourseDTO courseDTO) {
        Optional<Course> course = this._courseRepository.findById(courseId);

        if (course.isPresent()) {
            Course existingCourse = course.get();

            // Retrieve the existing values if they are not being updated
            var title = Optional.ofNullable(courseDTO.title())
                    .orElse(existingCourse.getCourseDetail().getTitle());
            var period = Optional.ofNullable(courseDTO.period())
                    .orElse(existingCourse.getCourseDetail().getPeriod().toString());
            var branche = Optional.ofNullable(courseDTO.branche())
                    .orElse(existingCourse.getCourseDetail().getBranche().toString());

            existingCourse.updateCourse(title, period, branche);
            this._courseRepository.save(existingCourse);
            return courseToDto(existingCourse);
        }
        return Optional.empty();
    }

    @Transactional
    public String removeCourseByTitle(String title)
    {
        if (this.checkIfCourseExistByTitle(title)) {
            this._courseRepository.deleteCourseByCourseDetail_Title(title);
            // Return empty string means delete was succesvol
            return "";
        }
        return "Course with title '" + title + "' not found";
    }

    // Create a material for a course
    @Transactional
    public Optional<CourseDTO> createCourseMaterial(String title, String subject) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetail_Title(title);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();
            course.createNewCourseMaterial(subject);
            this._courseRepository.save(course);

            return courseToDto(course);
        }

        return Optional.empty();
    }

    public Optional<CourseMaterialDTO> getCourseMaterialBySubject(String courseTitle, String subject) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetail_Title(courseTitle);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();

            CourseMaterial courseMaterial = course.getMaterialBySubject(subject);
            if (courseMaterial == null) {
                return Optional.empty();
            }
            return courseMaterialToDto(courseMaterial);
        }

        return Optional.empty();
    }

    // Creates a new course material module
    public Optional<CourseDTO> createCourseMaterialModule(String title, String subject, ModuleDTO moduleDTO) {
        Optional<Course> optionalCourse = this._courseRepository.findCourseByCourseDetail_Title(title);

        if (optionalCourse.isPresent()) {
            var course = optionalCourse.get();
            /* Check if the course material with the given material do exist.*/
            CourseMaterial courseMaterial = course.getMaterialBySubject(subject) ;

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

    private boolean checkIfCourseExistByTitle(String title) {
        return this._courseRepository.existsByCourseDetail_Title(title);
    }

    /* This section is to set an object to dto within builders*/
    private Optional<CourseDTO> courseToDto(Course course) {
        return Optional.ofNullable(CourseDTO.builder()
                .announcementDTOs(new ArrayList<>())
                .courseMaterialDTOs(this.courseMaterialsToDto(course.getCourseMaterialList()))
                .title(course.getCourseDetail().getTitle())
                .period(course.getCourseDetail().getPeriod().toString())
                .branche(course.getCourseDetail().getBranche().toString())
                .build());
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

    // Get announcements from announcement service and return it as dto.
/*
    private List<AnnouncementDTO> announcementToDto(Long courseId) {
        return announcementService.getAnnouncementByCursus(courseId)
                .stream()
                .map(announcement -> new AnnouncementDTO(
                        announcement.getAnnouncer(),
                        announcement.getContent(),
                        announcement.getCursus()
                ))
                .collect(Collectors.toList());
    }
*/

    public Object getCountCoursesPerBranche() {
        return this._courseRepository.countAllByCourseDetail_Branche();
    }

    public Object getCountCoursesPerPeriod(String periodAsString) {
        Period period = Period.valueOf(periodAsString.toUpperCase());
        return this._courseRepository.countAllByCourseDetail_Period(period);
    }
}
