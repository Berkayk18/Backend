package nl.hu.inno.humc.monoliet.courseplanning.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.course.domain.Course;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import nl.hu.inno.humc.monoliet.courseplanning.domain.Enrollment;
import nl.hu.inno.humc.monoliet.courseplanning.domain.User;
import nl.hu.inno.humc.monoliet.courseplanning.exceptions.ResourceNotFoundException;
import nl.hu.inno.humc.monoliet.courseplanning.presentation.dto.EnrollmentNamesTypeDTO;
import nl.hu.inno.humc.monoliet.courseplanning.repository.EnrollmentRepository;
import nl.hu.inno.humc.monoliet.courseplanning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public void enrollUserToCourse(Long courseId, Long userId, Enrollment.Type type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

        if (enrollmentRepository.findByUserIdAndCourseId(userId, courseId).isPresent()) {
            throw new ResourceNotFoundException("User with id " + userId + " is already enrolled in course with id " + courseId);
        }

        Enrollment enrollment = new Enrollment(user, course, type);
        enrollmentRepository.save(enrollment);
    }

    public List<EnrollmentNamesTypeDTO> getEnrollments(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        if (enrollments.isEmpty()) {
            throw new ResourceNotFoundException("Enrollment not found for course with id " + courseId);
        }

        return enrollments.stream()
                                       .map(this::toDto)
                                       .collect(Collectors.toList());
    }

    private EnrollmentNamesTypeDTO toDto(Enrollment enrollment) {
        return new EnrollmentNamesTypeDTO(enrollment.getType(), enrollment.getUser().getName());
    }
}
