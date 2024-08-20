package nl.hu.inno.humc.monoliet.course.domain;

import nl.hu.inno.humc.monoliet.course.domain.exceptions.DataAlreadyExistException;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.LimitExceededException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class CourseTest {
    @Test
    @DisplayName("Validate correct course title")
    public void CourseWithCorrectTitleReturnsNoError()
    {
        //Arrange + Act
        // New course is created with title
        var course = new Course("Backend Programming 3", "C", "IT");

        //Assert
        assertEquals("Backend Programming 3", course.getCourseDetail().getTitle());
    }

    @Test
    @DisplayName("Validate empty course title")
    public void CourseWithEmptyTitleThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Course("", "C", "IT");
        });

        //Assert
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Validate empty course period")
    public void CourseWithEmptyPeriodThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Course("Backend Programming 3", "", "IT");
        });

        //Assert
        assertEquals("Period cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Validate invalid course period")
    public void CourseWithInvalidPeriodThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Course("Backend Programming 3", "E", "IT");
        });

        //Assert
        assertEquals("Invalid period value", exception.getMessage());
    }

    @Test
    @DisplayName("Validate empty course branche")
    public void CourseWithEmptyBrancheThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Course("Backend Programming 3", "C", "");
        });

        //Assert
        assertEquals("Branche cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Validate invalid course branche")
    public void CourseWithInvalidBrancheThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Course("Backend Programming 3", "D", "Health");
        });

        //Assert
        assertEquals("Invalid branche value", exception.getMessage());
    }

    @Test
    @DisplayName("Update course details")
    public void UpdateCourseDetailsReturnCourseWithNewDetails()
    {
        //Arrange
        var course = new Course("Backend", "A", "Sport");

        //Act
        course.updateCourse("Backend Programming 3", "C", "IT");

        //Assert
        assertEquals("Backend Programming 3", course.getCourseDetail().getTitle());
        assertEquals("C", course.getCourseDetail().getPeriod().toString());
        assertEquals("IT", course.getCourseDetail().getBranche().toString());
    }

    @Test
    @DisplayName("Create new material with valid data")
    public void CreateNewMaterialThatDoesNotExistIncreaseMaterialsCount()
    {
        //Arrange
        var course = new Course("Backend Programming 3", "C", "IT");

        //Act
        course.createNewCourseMaterial("Monolit");

        //Assert
        assertEquals(1, course.getCourseMaterialList().size());
    }

    @Test
    @DisplayName("Get material by subject")
    public void GetCourseMaterialByExistingSubjectReturnsCourseMaterial()
    {
        //Arrange
        var course = new Course("Backend Programming 3", "C", "IT");
        course.createNewCourseMaterial("Monolit");

        //Act
        var actualResult = course.getMaterialBySubject("Monolit");

        //Assert
        assertEquals("Monolit", actualResult.getSubject());
        assertEquals(1, course.getCourseMaterialList().size());
    }

    @Test
    @DisplayName("Get material by subject that does not exist")
    public void GetCourseMaterialByUnexistingSubjectReturnNull()
    {
        //Arrange
        var course = new Course("Backend Programming 3", "C", "IT");
        course.createNewCourseMaterial("Monolit");

        //Act
        var actualResult = course.getMaterialBySubject("Microservices");

        //Assert
        assertNull(actualResult);
    }

    @Test
    @DisplayName("Create material with existing subject throws")
    public void CreateMaterialWithExistingSubjectThrowsAnError()
    {
        //Arrange
        var course = new Course("Backend Programming 3", "C", "IT");
        course.createNewCourseMaterial("Monolit");

        //Act
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class, () -> {
            course.createNewCourseMaterial("Monolit");
        });

        //Assert
        assertEquals("Material with the given data already exist", exception.getMessage());
    }

    @Test
    @DisplayName("Create material over the allowed limit throws")
    public void CreateMaterialAboveTheLimitThrowsAnError()
    {
        //Arrange
        var course = new Course("Backend Programming 3", "C", "IT");
        course.createNewCourseMaterial("Monolit");
        course.createNewCourseMaterial("Monolit2");
        course.createNewCourseMaterial("Monolit3");
        course.createNewCourseMaterial("Monolit4");
        course.createNewCourseMaterial("Monolit5");
        course.createNewCourseMaterial("Monolit6");
        course.createNewCourseMaterial("Monolit7");
        course.createNewCourseMaterial("Monolit8");
        course.createNewCourseMaterial("Monolit9");
        course.createNewCourseMaterial("Monolit10");

        //Act
        LimitExceededException exception = assertThrows(LimitExceededException.class, () -> {
            course.createNewCourseMaterial("Monolit11");
        });

        //Assert
        assertEquals("Cannot add more Materials, the maximum limit has been reached.", exception.getMessage());
    }
}
