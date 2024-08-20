package nl.hu.inno.humc.courseplanning.presentation.controller;

import nl.hu.inno.humc.courseplanning.application.service.EnrollmentService;
import nl.hu.inno.humc.courseplanning.presentation.dto.EnrollmentNamesTypeDTO;
import nl.hu.inno.humc.courseplanning.presentation.dto.EnrollmentUserIDTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/courses/{courseId}/enrollments")
    public ResponseEntity<String> enrollInCourse(@PathVariable("courseId") Long courseId,
                                                 @RequestBody EnrollmentUserIDTypeDTO enrollmentUserIDTypeDTO) {
        enrollmentService.enrollUserToCourse(courseId, enrollmentUserIDTypeDTO.getUserId(), enrollmentUserIDTypeDTO.getType());
        return ResponseEntity.ok("Enrolled in course as " + enrollmentUserIDTypeDTO.getType() + "!");
    }

    @GetMapping("/courses/{courseId}/enrollments")
    public ResponseEntity<List<EnrollmentNamesTypeDTO>> getEnrollments(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollments(courseId));
    }
}
