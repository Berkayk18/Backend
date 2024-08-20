package nl.hu.inno.humc.monoliet.course.application.api.fakers;

import nl.hu.inno.humc.monoliet.course.application.api.interfaces.IEnrollment;
import nl.hu.inno.humc.monoliet.course.application.dto.EnrollmentDTO;

import java.util.List;

public class FakeEnrollmentApi implements IEnrollment {
    @Override
    public List<EnrollmentDTO> getEnrollmentPerCourse(Long courseId) {
        return List.of(new EnrollmentDTO("Canvas", "There are no enrollments for this course"));
    }
}
