package nl.hu.inno.humc.courseplanning.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.courseplanning.application.dto.EnrollmentEventDTO;
import nl.hu.inno.humc.courseplanning.application.port.CourseServicePort;
import nl.hu.inno.humc.courseplanning.application.port.EnrollmentEventsPublisher;
import nl.hu.inno.humc.courseplanning.domain.CourseReference;
import nl.hu.inno.humc.courseplanning.domain.Enrollment;
import nl.hu.inno.humc.courseplanning.domain.User;
import nl.hu.inno.humc.courseplanning.exceptions.ResourceNotFoundException;
import nl.hu.inno.humc.courseplanning.presentation.dto.EnrollmentNamesTypeDTO;
import nl.hu.inno.humc.courseplanning.repository.EnrollmentRepository;
import nl.hu.inno.humc.courseplanning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseServicePort courseServicePort;
    private final EnrollmentEventsPublisher enrollmentEventsPublisher;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository,
                             @Qualifier("fakeCourseServiceAdapter") CourseServicePort courseServicePort,
                             EnrollmentEventsPublisher enrollmentEventsPublisher) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseServicePort = courseServicePort;
        this.enrollmentEventsPublisher = enrollmentEventsPublisher;
    }

    public void enrollUserToCourse(Long courseId, UUID userId, Enrollment.Type type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        if (!courseServicePort.doesCourseExist(courseId)) {throw new ResourceNotFoundException("Course not found with" +
                                                                                                       " " +
                                                                                                      "id " + courseId);}

        if (enrollmentRepository.findByUserIdAndCourseReference_CourseId(userId, courseId).isPresent()) {
            throw new ResourceNotFoundException("User with id " + userId + " is already enrolled in course with id " + courseId);
        }

        Enrollment enrollment = new Enrollment(user, new CourseReference(courseId), type);
        enrollmentRepository.save(enrollment);

        EnrollmentEventDTO enrollmentEventDTO = toEvent(enrollment);
        enrollmentEventsPublisher.publishCourseEnrollmentEvent(enrollmentEventDTO);
    }

    public void deleteEnrollments(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseReference_CourseId(courseId);
        if (enrollments.isEmpty()) {
            throw new ResourceNotFoundException("Enrollment not found for course with id " + courseId);
        }
        enrollments.forEach(enrollment -> {
            EnrollmentEventDTO enrollmentEventDTO = toEvent(enrollment);
            enrollmentEventsPublisher.publishEnrollmentDeletedEvent(enrollmentEventDTO);
        });
        enrollmentRepository.deleteAll(enrollments);

    }

    private EnrollmentEventDTO toEvent(Enrollment enrollment) {
        return new EnrollmentEventDTO(enrollment.getCourseReference().getCourseId(),enrollment.getUser().getName(), enrollment.getUser().getEmail().toString(), enrollment.getType());
    }

    public List<EnrollmentNamesTypeDTO> getEnrollments(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseReference_CourseId(courseId);

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
