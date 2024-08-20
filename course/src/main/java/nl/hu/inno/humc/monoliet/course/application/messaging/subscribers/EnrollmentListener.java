package nl.hu.inno.humc.monoliet.course.application.messaging.subscribers;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.course.application.messaging.CourseMessaging;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.EnrollmentMessageContract;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.UnexistingCourseMessage;
import nl.hu.inno.humc.monoliet.course.domain.Course;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions.DECREMENT;
import static nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions.INCREMENT;
import static nl.hu.inno.humc.monoliet.course.domain.enums.InstancesCount.ENROLLMENTS;

@Component
public class EnrollmentListener {
    private final CourseMessaging courseMessaging;
    private final CourseRepository courseRepository;

    public EnrollmentListener(CourseMessaging courseMessaging, CourseRepository courseRepository) {
        this.courseMessaging = courseMessaging;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @RabbitListener(queues = {"enrollment-created-queue"})
    public void handleNewEnrollment(EnrollmentMessageContract m)
    {
        Optional<Course> optionalCourse = this.courseRepository.findById(m.getCourseId());

        if (optionalCourse.isEmpty()) {
            this.courseMessaging.courseDoesNotExistMessage(
                    new UnexistingCourseMessage(m.getCourseId())
            );
        } else
        {
            var course = optionalCourse.get();
            course.addNewEnrollment(m.getUsername(), m.getUserType());
            course.updateInstanceCount(course.getTotalEnrollmentsPerCourse().getTotalEnrollments(),
                    ENROLLMENTS,
                    INCREMENT);
            this.courseRepository.save(course);
        }
    }

    @Transactional
    @RabbitListener(queues = {"enrollment-deleted-queue"})
    public void handleRemovedEnrollment(EnrollmentMessageContract m)
    {
        Optional<Course> optionalCourse = this.courseRepository.findById(m.getCourseId());

        if (optionalCourse.isEmpty()) {
            this.courseMessaging.courseDoesNotExistMessage(
                    new UnexistingCourseMessage(m.getCourseId())
            );
        } else
        {
            var course = optionalCourse.get();
            course.removeEnrollment(m.getUsername(), m.getUserType());
            course.updateInstanceCount(course.getTotalEnrollmentsPerCourse().getTotalEnrollments(),
                    ENROLLMENTS,
                    DECREMENT);
            this.courseRepository.save(course);
        }
    }
}
