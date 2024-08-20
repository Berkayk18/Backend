package nl.hu.inno.humc.courseplanning;

import nl.hu.inno.humc.courseplanning.application.service.EnrollmentService;
import nl.hu.inno.humc.courseplanning.application.service.UserService;
import nl.hu.inno.humc.courseplanning.domain.Email;
import nl.hu.inno.humc.courseplanning.domain.Enrollment;
import nl.hu.inno.humc.courseplanning.domain.User;
import nl.hu.inno.humc.courseplanning.exceptions.InvalidEmailException;
import nl.hu.inno.humc.courseplanning.exceptions.ResourceNotFoundException;
import nl.hu.inno.humc.courseplanning.repository.EnrollmentRepository;
import nl.hu.inno.humc.courseplanning.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnrollUserToCursusTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @BeforeAll
    public void setup() {
        userRepository.save(new User("testUser1", new Email("test@hu.nl")));
    }

    @AfterAll
    void cleanup() {
        enrollmentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void emailValidationFailsWithInvalidEmail() {
        String invalidEmail = "test2@mail.nl";
        assertThrows(InvalidEmailException.class, () -> {
            new Email(invalidEmail);
        });
    }

    @Test
    public void createUserSavesUserToDatabase() {
        String name = "testUser2";
        Email email = new Email("test2@hu.nl");

        userService.createUser(name, email);

        User user = userRepository.findByName(name);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    @Order(1)
    public void enrollUserToCursusSavesEnrollmentToDatabase() {
        User user = userRepository.findByName("testUser1");
        enrollmentService.enrollUserToCourse(1L, user.getId(), Enrollment.Type.TEACHER);

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseReference_CourseId(user.getId(), 1L).orElse(null);
        assertThat(enrollment).isNotNull();
        assertThat(enrollment.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Order(2)
    public void enrollUserToCursusThrowsExceptionWhenUserIsAlreadyEnrolled() {
        User user = userRepository.findByName("testUser1");
        UUID userId = user.getId();
        assertThatThrownBy(() -> enrollmentService.enrollUserToCourse(1L, userId, Enrollment.Type.TEACHER))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User with id " + userId + " is already enrolled in course with id " + 1L);
    }

    @Test
    public void ShouldReturnEnrollments() {
        assertThat(enrollmentService.getEnrollments(1L)).isNotNull();
    }
}
