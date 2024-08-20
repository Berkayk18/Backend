package nl.hu.inno.humc.courseplanning.repository;

import nl.hu.inno.humc.courseplanning.domain.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends MongoRepository<Enrollment, UUID> {
    List<Enrollment> findByCourseReference_CourseId(Long courseId);

    Optional<Enrollment> findByUserIdAndCourseReference_CourseId(UUID userId, Long courseId);
}
