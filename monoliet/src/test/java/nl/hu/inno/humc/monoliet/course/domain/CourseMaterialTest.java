package nl.hu.inno.humc.monoliet.course.domain;

import nl.hu.inno.humc.monoliet.course.domain.exceptions.DataAlreadyExistException;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.LimitExceededException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
public class CourseMaterialTest {
    @Test
    @DisplayName("Validate correct material subject")
    public void CourseMaterialWithCorrectSubjectReturnsNoError()
    {
        //Arrange + Act
        // New course is created with title
        var material = new CourseMaterial("Monolit");

        //Assert
        assertEquals("Monolit", material.getSubject());
    }

    @Test
    @DisplayName("Validate empty material subject")
    public void MaterialWithEmptySubjectThrowsException()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CourseMaterial("");
        });

        //Assert
        assertEquals("Subject cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Validate long subject")
    public void MaterialWithLongSubjectThrows()
    {

        // Arrange + Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CourseMaterial("Lorem ipsum Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        });

        //Assert
        assertEquals("Subject is too long", exception.getMessage());
    }

    @Test
    @DisplayName("Create new module with valid data")
    public void CreateNewModuleThatDoesNotExistIncreaseModuleCount()
    {
        //Arrange
        var material = new CourseMaterial("Monolit");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        Date futureDate = calendar.getTime();


        //Act
        material.createNewModule("Monolit opdracht", futureDate);

        //Assert
        assertEquals(1, material.getModules().size());
        assertEquals("Monolit opdracht", material.getModules().get(0).getModuleContent().getName());
        assertEquals(futureDate, material.getModules().get(0).getModuleContent().getDeadline());
    }

    @Test
    @DisplayName("Create module with existing name throws")
    public void GetCourseMaterialByExistingSubjectReturnsCourseMaterial()
    {
        //Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        Date futureDate = calendar.getTime();

        var material = new CourseMaterial("Monolit");
        material.createNewModule("Monolit opdracht", futureDate);

        //Act
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class, () -> {
            material.createNewModule("Monolit opdracht", futureDate);
        });

        //Assert
        assertEquals("Module with the given data already exist", exception.getMessage());

    }

    @Test
    @DisplayName("Create module over the allowed limit throws")
    public void CreateModuleAboveTheLimitThrowsAnError()
    {
        //Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        Date futureDate = calendar.getTime();

        var material = new CourseMaterial("Monolit");
        material.createNewModule("Monolit opdracht", futureDate);
        material.createNewModule("Monolit opdracht2", futureDate);
        material.createNewModule("Monolit opdracht3", futureDate);
        material.createNewModule("Monolit opdracht4", futureDate);
        material.createNewModule("Monolit opdracht5", futureDate);
        material.createNewModule("Monolit opdracht6", futureDate);
        material.createNewModule("Monolit opdracht7", futureDate);
        material.createNewModule("Monolit opdracht8", futureDate);
        material.createNewModule("Monolit opdracht9", futureDate);
        material.createNewModule("Monolit opdracht10", futureDate);

        //Act
        LimitExceededException exception = assertThrows(LimitExceededException.class, () -> {
            material.createNewModule("Monolit opdracht11", futureDate);
        });

        //Assert
        assertEquals("Cannot add more Modules, the maximum limit has been reached.", exception.getMessage());
    }
}
