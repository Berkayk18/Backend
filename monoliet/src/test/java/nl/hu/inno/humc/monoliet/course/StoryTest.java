package nl.hu.inno.humc.monoliet.course;

import nl.hu.inno.humc.monoliet.course.application.CourseService;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseMaterialDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.ModuleDTO;
import nl.hu.inno.humc.monoliet.course.domain.Course;
import nl.hu.inno.humc.monoliet.course.domain.CourseMaterial;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(PER_CLASS)
public class StoryTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private Course course;

    @BeforeEach
    public void MockRepositoryResults(){
        when(courseRepository.findCourseByCourseDetail_Title(eq("Backend Programming 2")))
                .thenReturn(Optional.of(new Course("Backend Programming 2", "B", "IT")));

        when(courseRepository.existsByCourseDetail_Title(eq("Backend Programming 2")))
                .thenReturn(true);

        when(courseRepository.findById(eq(1L)))
                .thenReturn(Optional.of(
                        new Course(
                                "Advanced Software Principles",
                                "D",
                                "IT"
                        )
                ));

        when(courseRepository.existsByCourseDetail_Title(eq("Frontend 2")))
                .thenReturn(true);

        when(courseRepository.existsByCourseDetail_Title(eq("Backend Programming 3")))
                .thenReturn(true);

        when(course.getMaterialBySubject(anyString())).thenReturn(new CourseMaterial("Programming Principles"));
    }

    @Test
    @DisplayName("As a course administrator, I want to manage courses, materials, and modules.")
    public void courseManagementStory() {
        // Story: Creating and Managing Courses, Materials, and Modules

        // Scenario 1: Create a new course
        courseService.createCourse( new CourseDTO(new ArrayList<>(), new ArrayList<>(),"Spieren opbouwen", "A", "sport"));
        courseService.createCourse( new CourseDTO(new ArrayList<>(), new ArrayList<>(),"CISQ 2", "C", "IT"));
        courseService.createCourse( new CourseDTO(new ArrayList<>(), new ArrayList<>(), "Backend Programming 3", "B", "IT"));

        // Scenario 2: Try to create an existing course
        Optional<CourseDTO> existingCourse = courseService.createCourse(
                new CourseDTO(new ArrayList<>(), new ArrayList<>(),"Backend Programming 2", "B", "IT"));

        assertTrue(existingCourse.isEmpty());

        // Scenario 3: Update an existing course by ID
        var updatedCourseDTO = new CourseDTO(
                new ArrayList<>(),
                new ArrayList<>(),
                "Advanced Software Principles",
                "D",
                "IT"
        );

        Optional<CourseDTO> updatedCourse = courseService
                .updateCourseById(1L, updatedCourseDTO);

        assertTrue(updatedCourse.isPresent());
        assertEquals(updatedCourseDTO, updatedCourse.get());


        // Scenario 4: Remove an existing course by title
        String errorMessage = courseService.removeCourseByTitle("Frontend 2");

        assertEquals("", errorMessage);

        // Scenario 5: Try to remove a non-existing course by title
        String notFoundMessage = courseService.removeCourseByTitle("Advanced SQL");

        assertEquals("Course with title 'Advanced SQL' not found", notFoundMessage);

        // Scenario 6: Create a new course material
        var createdMaterial = new CourseMaterialDTO(
                new ArrayList<>(),
                "Programming Principles"
        );

        Optional<CourseDTO> actualMaterial = courseService.createCourseMaterial(
                "Backend Programming 3", "Programming Principles");


        // Scenario 7: Create a new course material with a module
        var createdCourseWithModule = new CourseDTO(
                new ArrayList<>(),
                List.of(new CourseMaterialDTO(
                        List.of(new ModuleDTO("Module 1", new Date())),
                        "Programming Principles"
                )),
                "Backend Programming 3",
                "C",
                "IT"
        );

        Optional<CourseDTO> actualCourseWithModule = courseService.createCourseMaterialModule(
                "Backend Programming 3", "Programming Principles",
                new ModuleDTO("Module 1",  new Date()));
    }
}
