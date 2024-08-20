package nl.hu.inno.humc.monoliet.course.application.messaging.subscribers;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.course.application.messaging.CourseMessaging;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.AnnouncementMessageContract;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.UnexistingCourseMessage;
import nl.hu.inno.humc.monoliet.course.domain.Course;
import nl.hu.inno.humc.monoliet.course.repository.CourseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions.DECREMENT;
import static nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions.INCREMENT;
import static nl.hu.inno.humc.monoliet.course.domain.enums.InstancesCount.ANNOUNCEMENT;

@Component
public class AnnouncementListener {
    private final CourseMessaging courseMessaging;
    private final CourseRepository courseRepository;

    public AnnouncementListener(CourseMessaging courseMessaging, CourseRepository courseRepository) {
        this.courseMessaging = courseMessaging;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @RabbitListener(queues = {"announcement-created-queue"})
    public void handleNewAnnouncement(AnnouncementMessageContract m)
    {
        Optional<Course> optionalCourse = this.courseRepository.findById(m.getCourseId());

        if (optionalCourse.isEmpty()) {
            this.courseMessaging.courseDoesNotExistMessage(
                    new UnexistingCourseMessage(m.getCourseId())
            );
        } else
        {
            var course = optionalCourse.get();
            course.addNewAnnouncement(m.getAnnouncer(), m.getContent());
            course.updateInstanceCount(course.getTotalAnnouncementPerCourse().getTotalAnnouncement(),
                    ANNOUNCEMENT,
                    INCREMENT);
            this.courseRepository.save(course);
        }
    }

    @Transactional
    @RabbitListener(queues = {"announcement-deleted-queue"})
    public void handleRemovedAnnouncement(AnnouncementMessageContract m)
    {
        Optional<Course> optionalCourse = this.courseRepository.findById(m.getCourseId());

        if (optionalCourse.isEmpty()) {
            this.courseMessaging.courseDoesNotExistMessage(
                    new UnexistingCourseMessage(m.getCourseId())
            );
        } else
        {
            var course = optionalCourse.get();
            course.removeAnnouncement(m.getAnnouncer(), m.getContent());
            course.updateInstanceCount(course.getTotalAnnouncementPerCourse().getTotalAnnouncement(),
                    ANNOUNCEMENT,
                    DECREMENT);
            this.courseRepository.save(course);
        }
    }
}
