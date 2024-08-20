package nl.hu.inno.humc.monoliet.course.application.api.interfaces;

import nl.hu.inno.humc.monoliet.course.application.dto.EnrollmentDTO;

import java.util.List;

public interface IEnrollment {

    List<EnrollmentDTO> getEnrollmentPerCourse(Long courseId);
}
