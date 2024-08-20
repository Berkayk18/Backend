package nl.hu.inno.humc.monoliet.course.presentation;

import nl.hu.inno.humc.monoliet.course.application.CourseService;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.CourseMaterialDTO;
import nl.hu.inno.humc.monoliet.course.application.dto.ModuleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("course")
public class CourseController {
    private final CourseService _courseService;

    public CourseController(CourseService courseService) {
        _courseService = courseService;
    }

    @GetMapping(value = "{title}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCourseByTitle(@PathVariable String title) {
        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);

        Optional<CourseDTO> course = this._courseService.getCourseByTitle(decodedTitle);

        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course with title " + title + " does not exist");
    }

    @PostMapping()
    public ResponseEntity<?> createNewCourse(@Validated @RequestBody CourseDTO courseDTO) {
        try {
            Optional<CourseDTO> createdCourse = this._courseService.createCourse(courseDTO);

            if (createdCourse.isPresent()) {
                return ResponseEntity.ok(createdCourse.get());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course with the same title already exist");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCourseByTitle(@PathVariable Long id, @Validated @RequestBody CourseDTO courseDTO) {
        try {
            Optional<CourseDTO> updatedCourse = this._courseService.updateCourseById(id, courseDTO);
            if (updatedCourse.isPresent()) {
                return ResponseEntity.ok(updatedCourse.get());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course with id: " + id + " does not exist");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "{title}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> removeCourseByTitle(@PathVariable String title) {
        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);
        String errorMessage = this._courseService.removeCourseByTitle(decodedTitle);
        if (errorMessage == null || errorMessage.isEmpty()) {
            return ResponseEntity.ok("Course with title " + title + " is successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @GetMapping(value = "{title}/{subject}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCourseByTitle(@PathVariable String title, @PathVariable String subject) {
        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);
        String decodedSubject = URLDecoder.decode(subject, StandardCharsets.UTF_8);

        Optional<CourseDTO> course = this._courseService.getCourseByTitle(decodedTitle);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with the given title " + decodedTitle + " does not exist.");
        }

        Optional<CourseMaterialDTO> courseMaterial = this._courseService.getCourseMaterialBySubject(decodedTitle, decodedSubject);

        if (courseMaterial.isPresent()) {
            return ResponseEntity.ok(courseMaterial.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course material with subject " + subject + " does not exist");
    }

    @PostMapping("{title}/material")
    public ResponseEntity<?> createCourseMaterial(@PathVariable String title, @Validated @RequestBody CourseMaterialDTO courseMaterialDTO) {
        try {
            Optional<CourseDTO> course = this._courseService.getCourseByTitle(title);

            if (course.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with the given title does not exist.");
            }

            Optional<CourseDTO> createdCourseMaterial = this._courseService.createCourseMaterial(title, courseMaterialDTO.subject());

            if (createdCourseMaterial.isPresent()) {
                return ResponseEntity.ok(createdCourseMaterial.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create course material.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("{title}/{material}/module")
    public ResponseEntity<?> createCourseMaterialModule(@PathVariable String title, @PathVariable String material, @Validated @RequestBody ModuleDTO moduleDTO)
    {
        try {
            Optional<CourseDTO> course = this._courseService.getCourseByTitle(title);

            if (course.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with the given title does not exist.");
            }

            var createdCourseMaterialModule = this._courseService.createCourseMaterialModule(title, material, moduleDTO);

            if (createdCourseMaterialModule.isPresent()) {
                return ResponseEntity.ok(createdCourseMaterialModule.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create course material.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    // Non-CRUD use cases
    @GetMapping(value = "/branches/{searchTarget}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCountCoursesPerBranche(@PathVariable String searchTarget)
    {
        try
        {
            if (searchTarget == null || searchTarget.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Search target can't be empty");
            }
            searchTarget = searchTarget.toUpperCase();
            if (searchTarget.equals("COUNT"))
            {
               var countCoursesPerBranche = this._courseService.getCountCoursesPerBranche();
               if (countCoursesPerBranche == null)
               {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cant find courses in any branch");
               }
                return ResponseEntity.ok(countCoursesPerBranche);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Search target is not allowed");
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @GetMapping(value = "/periods/{period}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCountCoursesPerPeriod(@PathVariable String period)
    {
        try
        {
            if (period == null || period.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Period can't be empty");
            }
            var countCoursesPerPeriode = this._courseService.getCountCoursesPerPeriod(period);
            if (countCoursesPerPeriode == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cant find courses in the given period");
            }
            return ResponseEntity.ok(countCoursesPerPeriode);

        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}
