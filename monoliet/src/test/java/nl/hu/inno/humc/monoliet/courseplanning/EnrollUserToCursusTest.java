//package nl.hu.inno.humc.monoliet.courseplanning;
//
//import nl.hu.inno.humc.monoliet.coursematerial.application.CourseService;
//import nl.hu.inno.humc.monoliet.coursematerial.application.dto.CourseDTO;
//import nl.hu.inno.humc.monoliet.courseplanning.application.EnrollmentService;
//import nl.hu.inno.humc.monoliet.courseplanning.application.UserService;
//import nl.hu.inno.humc.monoliet.courseplanning.domain.Email;
//import nl.hu.inno.humc.monoliet.courseplanning.domain.Enrollment;
//import nl.hu.inno.humc.monoliet.courseplanning.domain.User;
//import nl.hu.inno.humc.monoliet.courseplanning.exceptions.InvalidEmailException;
//import nl.hu.inno.humc.monoliet.courseplanning.exceptions.ResourceNotFoundException;
//import nl.hu.inno.humc.monoliet.courseplanning.repository.EnrollmentRepository;
//import nl.hu.inno.humc.monoliet.courseplanning.repository.UserRepository;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class EnrollUserToCursusTest {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
//    @Test
//    @Order(1)
//    public void invalidEmailUserThrowsException() {
//        String name = "testUser";
//        Email email = new Email("test@mail.nl");
//
//        assertThatThrownBy(() -> userService.createUser(name, email))
//                .isInstanceOf(InvalidEmailException.class)
//                .hasMessageContaining("Invalid HU email address: " + email);
//    }
//
//    @Test
//    @Order(2)
//    public void createUserSavesUserToDatabase() {
//        String name = "testUser";
//        Email email = new Email("test@hu.nl");
//
//        userService.createUser(name, email);
//
//        User user = userRepository.findByName(name);
//        assertThat(user).isNotNull();
//        assertThat(user.getName()).isEqualTo(name);
//    }
//
//    @Test
//    @Order(3)
//    public void enrollUserToCursusSavesEnrollmentToDatabase() {
//        // create course here otherwise it will fail
//        //courseService.createCourse(new CourseDTO(new ArrayList<>(),"FEP 3", "2023-2024 C", new Date()));
//        enrollmentService.enrollUserToCourse(802L, 1L, Enrollment.Type.TEACHER);
//
//        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(1L, 1L).orElse(null);
//        assertThat(enrollment).isNotNull();
//        assertThat(enrollment.getUser().getId()).isEqualTo(1L);
//    }
//
//    @Test
//    @Order(4)
//    public void enrollUserToCursusThrowsExceptionWhenUserIsAlreadyEnrolled() {
//        assertThatThrownBy(() -> enrollmentService.enrollUserToCourse(1L, 1L, Enrollment.Type.TEACHER))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessageContaining("Enrollment not found for course with id " + 1L);
//    }
//
//    @Test
//    @Order(5)
//    public void ShouldReturnEnrollments() {
//        assertThat(enrollmentService.getEnrollments(1L)).isNotNull();
//    }
//}
